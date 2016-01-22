package com.example.pulltorefreshlistview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.pulltorefreshlistview.view.MyListView;
import com.example.pulltorefreshlistview.view.OnRefreshListener;

/**
 * 
 * @author keily
 *
 * @param <S> �б���itemչʾ���������� ����Ĭ�϶���Map<String,Object>
 */
public abstract class MyListActivity<S> extends Activity implements OnRefreshListener {

	private MyListAdapter<S> adapter;
	private MyListView mListView;
	
	protected int pageCount = 10;//ÿҳitem����
	protected int pageSize = 1;//ҳ��
	
	private boolean isRefresh = true;//�Ƿ�����ˢ���б�
	
	private Map<String,String> paramMap= null;//��ѯ����map

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		initListView();
		paramMap = generateModelByIntent();
		fetchListInfo(true);//��ȡ�б�����
	}

	/**
	 * ��ʼ��listview 
	 */
	private void initListView() {
		mListView = (MyListView) findViewById(R.id.refreshlistview);
		mListView.setOnRefreshListener(this);//ע��������������
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(null==adapter) return;
				S item = adapter.getItem(position);
				if(null!=item)
					gotoDetail(item);
			}
		});
	}
	
	/**
	 * ��ȡ�б���ģ������
	 * @param refresh
	 */
	private void fetchListInfo(boolean refresh){
		if (isRefresh = refresh) {
			pageSize = 1; //ˢ���б����� �ӵ�һҳ��ȡ����
		} else {
			pageSize ++;
		}
		new Thread(new Runnable(){
			public void run(){
				List<S> resultModel = getListModel(paramMap);//Զ�̵��÷�����ȡ�б�����
				Message message = Message.obtain();
				message.obj = resultModel;
				HANDLER.sendMessage(message);
			}
		}).start();
	}
	
	@SuppressLint("HandlerLeak")
	private Handler HANDLER = new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message message){
//			LoadingDialog.hide();
			List<S> resultModel =  (List<S>)message.obj;
			if(null==resultModel){
				Toast.makeText(MyListActivity.this, "û�и���������", Toast.LENGTH_LONG).show();
				return;//R.string.no_more_data_text
			}
			
			if(null==resultModel||0>=resultModel.size()){
				Toast.makeText(MyListActivity.this,"û�и���������", Toast.LENGTH_LONG).show();
				return;// R.string.no_more_data_text
			}
			
			updateListView(resultModel, isRefresh);
		}
	};
	
	protected void updateListView(List<S> modelList, boolean isRefresh){
		if (null != modelList && modelList.size() > 0) {
			if (isRefresh) {	
				if(adapter == null){
					adapter = generateListAdapter();
				}
				adapter.clearData();
				adapter.addResult(modelList);
				mListView.setAdapter(adapter);
			} else {
				adapter.addResult(modelList);
				adapter.notifyDataSetChanged();
			}
		} 
	}
	
	/**
	 * ͨ�������ȡ�б�ģ��
	 * @return �����б�ģ�͵Ľ��ģ��
	 * */
	protected abstract List<S> getListModel(Map<String,String> pageModel);
	
	/**
	 * ��ȡ��ѯ����map ��Ҫ������д
	 * @return ����ģ��
	 * */
	protected abstract Map<String,String> generateModelByIntent();
	
	/**
	 * ��ת����ϸҳ��
	 * */
	protected abstract void gotoDetail(S model);
	
	/**
	 * ��ȡ�б�������
	 * */
	protected abstract MyListAdapter<S> generateListAdapter();

	/**
	 * �б�������
	 * @author keily
	 *
	 * @param <Result>
	 */
	protected abstract class MyListAdapter<Result> extends BaseAdapter {

		protected List<Result> modelList;
		protected LayoutInflater mInflater;
		
		public MyListAdapter(Context mContext){
			mInflater = (LayoutInflater) mContext.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public int getCount() {
			return (null!=modelList?modelList.size():0);
		}

		@Override
		public Result getItem(int position) {
			return (null==modelList||position>modelList.size()?null:modelList.get(position));
		}

		@Override
		public long getItemId(int position) {
			
			return position;
		}
		
		/**
		 * ���һ���б������Ԫ�ص���ǰ���б���
		 * @param modelList Ҫ��ӵ��б�
		 * */
		public void addResult(List<Result> modelList) {
			if(this.modelList == null){
				this.modelList = new ArrayList<Result>();
			}
			this.modelList.addAll(modelList);
		}
		
		/**
		 * ����������ݣ����б�ˢ��ʱ��Ҫɾ�������������
		 * */
		public void clearData() {
			if(modelList == null) return;
			modelList.clear();
		}


	}

	@Override
	public void onDownPullRefresh() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				SystemClock.sleep(2000);
				fetchListInfo(true);//ˢ���б�����
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();
				mListView.hideHeaderView();
			}
		}.execute(new Void[] {});
	}

	@Override
	public void onLoadingMore() {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				SystemClock.sleep(5000);
				fetchListInfo(false);//��ȡ��������
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();

				// ���ƽŲ�������
				mListView.hideFooterView();
			}
		}.execute(new Void[] {});
	}

}
