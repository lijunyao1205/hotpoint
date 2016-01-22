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
 * @param <S> 列表中item展示的数据类型 我们默认都是Map<String,Object>
 */
public abstract class MyListActivity<S> extends Activity implements OnRefreshListener {

	private MyListAdapter<S> adapter;
	private MyListView mListView;
	
	protected int pageCount = 10;//每页item数量
	protected int pageSize = 1;//页码
	
	private boolean isRefresh = true;//是否重新刷新列表
	
	private Map<String,String> paramMap= null;//查询参数map

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		initListView();
		paramMap = generateModelByIntent();
		fetchListInfo(true);//获取列表数据
	}

	/**
	 * 初始化listview 
	 */
	private void initListView() {
		mListView = (MyListView) findViewById(R.id.refreshlistview);
		mListView.setOnRefreshListener(this);//注册上拉下拉方法
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
	 * 获取列表中模型数据
	 * @param refresh
	 */
	private void fetchListInfo(boolean refresh){
		if (isRefresh = refresh) {
			pageSize = 1; //刷新列表数据 从第一页获取数据
		} else {
			pageSize ++;
		}
		new Thread(new Runnable(){
			public void run(){
				List<S> resultModel = getListModel(paramMap);//远程调用方法获取列表数据
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
				Toast.makeText(MyListActivity.this, "没有更多数据了", Toast.LENGTH_LONG).show();
				return;//R.string.no_more_data_text
			}
			
			if(null==resultModel||0>=resultModel.size()){
				Toast.makeText(MyListActivity.this,"没有更多数据了", Toast.LENGTH_LONG).show();
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
	 * 通过网络获取列表模型
	 * @return 带有列表模型的结果模型
	 * */
	protected abstract List<S> getListModel(Map<String,String> pageModel);
	
	/**
	 * 获取查询参数map 需要子类重写
	 * @return 搜索模型
	 * */
	protected abstract Map<String,String> generateModelByIntent();
	
	/**
	 * 跳转到详细页面
	 * */
	protected abstract void gotoDetail(S model);
	
	/**
	 * 获取列表适配器
	 * */
	protected abstract MyListAdapter<S> generateListAdapter();

	/**
	 * 列表适配器
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
		 * 添加一个列表的所有元素到当前的列表中
		 * @param modelList 要添加的列表
		 * */
		public void addResult(List<Result> modelList) {
			if(this.modelList == null){
				this.modelList = new ArrayList<Result>();
			}
			this.modelList.addAll(modelList);
		}
		
		/**
		 * 清除所有数据，在列表刷新时需要删除清除所有数据
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
				fetchListInfo(true);//刷新列表数据
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
				fetchListInfo(false);//获取更多数据
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				adapter.notifyDataSetChanged();

				// 控制脚布局隐藏
				mListView.hideFooterView();
			}
		}.execute(new Void[] {});
	}

}
