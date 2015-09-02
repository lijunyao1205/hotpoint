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
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.model.CrimeLib;
import com.bignerdranch.android.criminalintent.util.DateUtils;

/**
 * 显示crimelist的fragment
 * 
 * @author keily
 * 
 */
public class CrimeListFragment extends ListFragment {

	private static String TAG = "CrimeListFragment";
	private List<Crime> mCrimes;
	private boolean mSubtitleVisible;//用来标记subtitle是否显示的标记
	private ListView mListView;//列表视图
	private View mEmptyView;//空视图
	private Button mNewCrimeButton;//添加crime button

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);//通知fragmentManager 调用操作栏菜单回调方法
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLib.getInstance(getActivity()).getCrimes();

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
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
		}
		mEmptyView = (View)view.findViewById(R.id.view_empty_crime);
		mListView = (ListView) view.findViewById(android.R.id.list);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
			//高版本sdk,对listview启用多选模式
			mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);//这里出错写成了ListView.CHOICE_MODE_MULTIPLE 导致不出来上下文菜单
			mListView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
				
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//					Log.d(TAG, "in onPrepareActionMode() ");
					return false;
				}
				
				public void onDestroyActionMode(ActionMode mode) {
//					Log.d(TAG, "in onDestroyActionMode() ");
				}
				
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					Log.d(TAG, "in onCreateActionMode() 初始化上下文菜单");
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.crime_list_item_context, menu);
					return true;
				}
				
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
					Log.d(TAG, "in onActionItemClicked() 处理上下文菜单事件");
					int itemId = item.getItemId();
					switch(itemId){
					case R.id.menu_item_delete_crime:
						ListAdapter adapter = (ListAdapter)mListView.getAdapter();
						for(int i=adapter.getCount()-1;i>=0;i--){
							if(getListView().isItemChecked(i)){
								Crime crime = (Crime)adapter.getItem(i);
								CrimeLib.getInstance(getActivity()).delete(crime);
							}
						}
						mode.finish();
						adapter.notifyDataSetChanged();//更新数据模型
						if(adapter.getCount()==0){
							mEmptyView.setVisibility(View.VISIBLE);
							mListView.setEmptyView(mEmptyView);
						}
						
						return true;
					default:
						return false;
					}
				}
				
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) {
//					Log.d(TAG, "in onItemCheckedStateChanged() checked:"+checked+",position:"+position);
					
				}
			});
		}
		else{
			//低版本sdk上使用 注册上下文菜单
			this.registerForContextMenu(mListView);//注册上下文菜单
		}
		
		mNewCrimeButton = (Button)view.findViewById(R.id.add_new_crime_button);
		if(CrimeLib.getInstance(getActivity()).getCrimes().size()<=0){
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
		
		mEmptyView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(getActivity(), "hello", Toast.LENGTH_LONG).show();
				getActivity().startActionMode(callback);
				return true;
			}
		});
		
		mNewCrimeButton.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(getActivity(), "hello", Toast.LENGTH_LONG).show();
				getActivity().startActionMode(callback);
				return true;
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
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			
			
		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater infalter = mode.getMenuInflater();
			infalter.inflate(R.menu.fragment_crime_list, menu);
			return true;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			int itemId = item.getItemId();
			switch(itemId){
			case R.id.menu_item_new_crime:{
				addCrime();//执行新增对象的操作
			}
			default:
				return false;
			}
		}
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public void onListItemClick(ListView l,View v,int position,long id){
		/*ArrayAdapter<Crime> arrayAdapter = ((ArrayAdapter<Crime>)l.getAdapter());*/
		ArrayAdapter<Crime> arrayAdapter = (ArrayAdapter<Crime>)getListAdapter();
		Crime crime = arrayAdapter.getItem(position);
		Intent intent = new Intent(getActivity(), CrimePaperActivity.class);
		intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
		startActivity(intent);
	}
	
	private class ListAdapter extends ArrayAdapter<Crime>{

		public ListAdapter(List<Crime> mCrimes){
			super(getActivity(), 0, mCrimes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			//判断 并初始化convertView （列表项实例）
			if(null==convertView){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
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
		if(CrimeLib.getInstance(getActivity()).getCrimes().size()>0){
			if(null!=mEmptyView){
				mEmptyView.setVisibility(View.INVISIBLE);
			}
		}
	}
	
	/**
	 * 生成操作栏菜单
	 */
	@Override
	 public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		MenuItem item = menu.findItem(R.id.menu_item_show_subtitle);
		if(mSubtitleVisible&&item!=null){
			item.setTitle(R.string.hide_subtitle);
		}
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	 public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		switch(itemId){
		case R.id.menu_item_new_crime:
			addCrime();
			return true;
		case R.id.menu_item_show_subtitle:
			if(getActivity().getActionBar().getSubtitle()!=null){
				mSubtitleVisible = false;
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
			}
			else{
				mSubtitleVisible = true;
				getActivity().getActionBar().setSubtitle(R.string.show_subtitle);
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
		CrimeLib.getInstance(this.getActivity()).addCrime(crime);
		Intent intent = new Intent(getActivity(), CrimePaperActivity.class);
		intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
//			startActivity(intent);
		startActivityForResult(intent, 0);
	}
	
	/**
	 * 初始化上下文菜单
	 */
	public void onCreateContextMenu(ContextMenu menu,View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		//v 视图对象就是 长按界面中的对象，触发的对象，可以根据该对象的id，生成不同的视图对象
		Log.d(TAG, "view:"+v.getId()+","+v.getClass());
		int viewId = v.getId();
		if(viewId==android.R.id.list){
			Log.d(TAG, "触发listview id:"+viewId);
			getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
		}
	}
	
	/**
	 * 响应上下文菜单
	 */
	public boolean onContextItemSelected(MenuItem item){
		int itemId = item.getItemId();
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo)item.getMenuInfo();
		int position = menuInfo.position;
		ListAdapter adapter  = (ListAdapter)getListAdapter();
		Crime crime = (Crime) getListAdapter().getItem(position);
		switch(itemId){
		//删除菜单项
		case R.id.menu_item_delete_crime:
			CrimeLib.getInstance(getActivity()).delete(crime);
			adapter.notifyDataSetChanged();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	
	

}
