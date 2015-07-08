/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLib.getInstance(getActivity()).getCrimes();

		ArrayAdapter<Crime> arrayAdapter = new ListAdapter(mCrimes);
		
		setListAdapter(arrayAdapter);
	}
	
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
	}

}
