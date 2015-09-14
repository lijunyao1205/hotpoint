/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.model.CrimeLib;
import com.bignerdranch.android.criminalintent.util.DateUtils;

/**
 *  显示crimelist的fragment
 * @author keily
 *
 */
public class CrimeListFragment extends SherlockListFragment {

	private static String TAG = "CrimeListFragment";
	private List<Crime> mCrimes;
	private boolean mSubtitleVisible;//用来标记subtitle是否显示的标记
	private ListView mListView;//列表视图
	private View mEmptyView;//空视图
	private Button mNewCrimeButton;//添加crime button
	private boolean isInMulitiSelect = false;//是否在多选模式 的控制变量
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);//通知fragmentManager 调用操作栏菜单回调方法
		getSherlockActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLib.getInstance(getSherlockActivity()).getCrimes();

		ArrayAdapter<Crime> arrayAdapter = new ListAdapter(mCrimes);
		
		setListAdapter(arrayAdapter);
		mSubtitleVisible = false;//初始化suttitle是否显示标记
		setRetainInstance(true);//保留fragment实例
		
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		ViewGroup view = (ViewGroup)inflater.inflate(R.layout.fragment_empty_crime, container, false);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
			if(mSubtitleVisible){
				getSherlockActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
		}
		mEmptyView = (View)view.findViewById(R.id.view_empty_crime);
		mListView = (ListView) view.findViewById(android.R.id.list);
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);//兼容老版本，使用比较老的多选方式
		mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				getSherlockActivity().startActionMode(callback);
				return true;
			}
		});
		
		mNewCrimeButton = (Button)view.findViewById(R.id.add_new_crime_button);
		if(CrimeLib.getInstance(getSherlockActivity()).getCrimes().size()<=0){
			mListView.setEmptyView(mEmptyView);
		}
		else{
			mEmptyView.setVisibility(View.INVISIBLE);
		}
		
		mNewCrimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addCrime();
			}
		});
		
		
		return view;
	}
	
	/**
	 * ActionModel 对象 ，
	 * 提供了空视图和视图里边的按钮 长按屏幕和按钮的操作，执行一个上下文视图对象
	 */
	
	@SuppressLint("NewApi")
	private ActionMode.Callback callback = new ActionMode.Callback(){

		@Override
		public boolean onCreateActionMode(ActionMode mode,
				com.actionbarsherlock.view.Menu menu) {
			
			MenuInflater infalter = mode.getMenuInflater();
			infalter.inflate(R.menu.crime_list_item_context, menu);//  fragment_crime_list
			isInMulitiSelect = true;
			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode,
				com.actionbarsherlock.view.Menu menu) {
			Log.d(TAG, "in onPrepareActionMode() ");
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			Log.d(TAG, "in onActionItemClicked() 处理上下文菜单事件");
			int itemId = item.getItemId();
			switch(itemId){
			case R.id.menu_item_delete_crime:
				ListAdapter adapter = (ListAdapter)mListView.getAdapter();
				for(int i=adapter.getCount()-1;i>=0;i--){
					if(getListView().isItemChecked(i)){
						Crime crime = (Crime)adapter.getItem(i);
						CrimeLib.getInstance(getSherlockActivity()).delete(crime);
					}
				}
				mode.finish();
				adapter.notifyDataSetChanged();//更新数据模型
				if(adapter.getCount()==0){
					mEmptyView.setVisibility(View.VISIBLE);
					mListView.setEmptyView(mEmptyView);
				}
				isInMulitiSelect = false;
				return true;
			default:
				return false;
			}
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			Log.d(TAG, "in onDestroyActionMode() ");
		}
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public void onListItemClick(ListView l,View v,int position,long id){
		if(isInMulitiSelect) return;//进入多选模式中
		/*ArrayAdapter<Crime> arrayAdapter = ((ArrayAdapter<Crime>)l.getAdapter());*/
		ArrayAdapter<Crime> arrayAdapter = (ArrayAdapter<Crime>)getListAdapter();
		Crime crime = arrayAdapter.getItem(position);
		Intent intent = new Intent(getSherlockActivity(), CrimePaperActivity.class);
		intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
		startActivity(intent);
	}
	
	private class ListAdapter extends ArrayAdapter<Crime>{

		public ListAdapter(List<Crime> mCrimes){
			super(getSherlockActivity(), 0, mCrimes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			//判断 并初始化convertView （列表项实例）
			if(null==convertView){
				convertView = getSherlockActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			}
			Crime crime = getItem(position);
			TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(crime.getTitle());
			TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(DateUtils.formateDate2WeekMmDDYYYY(crime.getDate()));
			CheckBox sovedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_sovedCheckBox);
			sovedCheckBox.setChecked(crime.isSoved());
			
			return convertView;
		}
		
	}
	
	/**
	 * CrimeListActivity恢复运行状态后，操作系统会向它发出调用onResume()生命周期方法
	 * 的指令。CrimeListActivity接到指令后，它的FragmentManager会调用当前被activity托管的
	 *	fragment的onResume()方法。这里，CrimeListFragment即唯一的目标fragment。
	 */
	@Override
	public void onResume(){
		super.onResume();
		ListAdapter adapter = (ListAdapter) getListAdapter();
		adapter.notifyDataSetChanged();
		if(CrimeLib.getInstance(getSherlockActivity()).getCrimes().size()>0){
			if(null!=mEmptyView){
				mEmptyView.setVisibility(View.INVISIBLE);
			}
		}
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		Log.d(TAG, "in crimeListFragemt onPause()");
		CrimeLib.getInstance(getSherlockActivity()).saveCrimes();//当listFragment暂停时，保存crimes
	}
	
	/**
	 * 生成操作栏菜单
	 */
	@Override
	 public void onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu, com.actionbarsherlock.view.MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		MenuItem item = menu.findItem(R.id.menu_item_show_subtitle);
		if(mSubtitleVisible&&item!=null){
			item.setTitle(R.string.hide_subtitle);
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	 public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item){
		int itemId = item.getItemId();
		switch(itemId){
		case R.id.menu_item_new_crime:
			addCrime();
			return true;
		case R.id.menu_item_show_subtitle:
			if(getSherlockActivity().getActionBar().getSubtitle()!=null){
				mSubtitleVisible = false;
				getSherlockActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
			}
			else{
				mSubtitleVisible = true;
				getSherlockActivity().getActionBar().setSubtitle(R.string.show_subtitle);
				item.setTitle(R.string.hide_subtitle);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 添加crime 方法
	 */
	private void addCrime() {
		Crime crime = new Crime();
		CrimeLib.getInstance(this.getSherlockActivity()).addCrime(crime);
		Intent intent = new Intent(getSherlockActivity(), CrimePaperActivity.class);
		intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
//			startActivity(intent);
		startActivityForResult(intent, 0);
	}
	
	/**
	 * 初始化上下文菜单
	 */
	/*public void onCreateContextMenu(ContextMenu menu,View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		//v 视图对象就是 长按界面中的对象，触发的对象，可以根据该对象的id，生成不同的视图对象
		Log.d(TAG, "view:"+v.getId()+","+v.getClass());
		int viewId = v.getId();
		if(viewId==android.R.id.list){
			Log.d(TAG, "触发listview id:"+viewId);
			getSherlockActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
		}
	}*/
	
	/**
	 * 响应上下文菜单
	 */
	/*public boolean onContextItemSelected(MenuItem item){
		int itemId = item.getItemId();
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		int position = menuInfo.position;
		ListAdapter adapter  = (ListAdapter)getListAdapter();
		Crime crime = (Crime) getListAdapter().getItem(position);
		switch(itemId){
		//删除菜单项
		case R.id.menu_item_delete_crime:
			CrimeLib.getInstance(getSherlockActivity()).delete(crime);
			adapter.notifyDataSetChanged();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}*/
}
