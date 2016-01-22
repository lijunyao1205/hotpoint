/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony.Mms.Part;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.model.CrimeLib;
import com.bignerdranch.android.criminalintent.util.DateUtils;

/**
 * 陋习 fragment
 * 
 * @author keily
 * 
 */
public class CrimeFragment extends Fragment {

	private static String TAG = "CrimeFragment";
	public static final String EXTRA_CRIME_ID ="com.bignerdranch.android.criminalintent.activity.CrimeFragment.EXTRA_CRIME_ID";
	private static CrimeFragment sCrimeFragment;
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private Button mTimeButton;
	private CheckBox mSovledCheckBox;
	private static SimpleDateFormat JDATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss ms", Locale.CHINA);
	private static DateFormat ADATE_FORMAT = new DateFormat();
	private static String DIALOG_DATE = "date";
	private static String DIALOG_TIME = "time";
	private static String DIALOG_CHOOSE = "choose";
	private static final int REQUEST_DATE =0;
	private static final int REQUEST_TIME =1;
	private static final int REQUEST_CHOOSE = 2;
	
	public static CrimeFragment getInstance(UUID crimeID){
		sCrimeFragment = new CrimeFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(EXTRA_CRIME_ID, crimeID);
		sCrimeFragment.setArguments(bundle);
		return sCrimeFragment;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "CrimeFragment onCreate()");
		super.onCreate(savedInstanceState);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
			if(NavUtils.getParentActivityName(getActivity())!=null){
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		setHasOptionsMenu(true);//设置操作栏菜单启用
		mCrime = new Crime();
		if(null!= sCrimeFragment){
			Bundle bundle = getArguments();
			UUID crimeId = (UUID)bundle.getSerializable(EXTRA_CRIME_ID);
			mCrime = CrimeLib.getInstance(getActivity()).getCrime(crimeId);
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings({ "deprecation", "static-access" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		Log.d(TAG, "CrimeFragment onCreateView()");
		super.onCreateView(inflater, parent, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_crime, parent, false);
		mTitleField = (EditText) view.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mCrime.setTitle(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		mDateButton = (Button) view.findViewById(R.id.crime_date);
		StringBuilder pattern = formatDate();
		mDateButton.setText(pattern);
		mDateButton.setEnabled(true);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickeFragment dialog = DatePickeFragment.getInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);//设置目标fragment，方便参数传递
				dialog.show(fm,DIALOG_DATE);*/
				FragmentManager fm = getActivity().getSupportFragmentManager();
				ChooseDateTimeFragment dialog = ChooseDateTimeFragment.newInstance();
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_CHOOSE);//设置目标fragment，方便参数传递
				dialog.show(fm,DIALOG_CHOOSE);
				
			}
		});
		
		mTimeButton = (Button)view.findViewById(R.id.crime_time);
		mTimeButton.setText(DateUtils.formateDate2HourMinute(mCrime.getDate(), DateUtils.PATTERN_HOUR_MINUTE));
		mTimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
				TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
				dialog.show(fm, DIALOG_TIME);
				
			}
		} );
		mTimeButton.setVisibility(View.GONE);
		

		mSovledCheckBox = (CheckBox) view.findViewById(R.id.crime_soved);
		mSovledCheckBox
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						mCrime.setSoved(isChecked);
					}
				});
		mSovledCheckBox.setChecked(mCrime.isSoved());
		return view;
	}

	/**
	 * 格式化日期字符串
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private StringBuilder formatDate() {
		StringBuilder pattern = new StringBuilder();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
		calendar.setTime(mCrime.getDate());
		String week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CHINA);
		pattern.append(week).append(",");
		String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CHINA);
		pattern.append(month).append(" ");
		pattern.append(calendar.get(Calendar.DAY_OF_MONTH)).append(",");
		pattern.append(calendar.get(Calendar.YEAR));
		pattern.append(", ");
		pattern.append(calendar.get(Calendar.HOUR_OF_DAY)).append("时");
		pattern.append(calendar.get(Calendar.MINUTE)).append("分");
		return pattern;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(TAG, "CrimeFragment onAttach()");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(TAG, "CrimeFragment onActivityCreated()");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "CrimeFragment onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "CrimeFragment onResume()");
	}
	
	@Override
	public void onPause(){
		super.onPause();
		CrimeLib.getInstance(getActivity()).saveCrimes();//保存crimes 到设备文件
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
		case REQUEST_DATE:
			if(resultCode==Activity.RESULT_OK){
				Date date = (Date)data.getSerializableExtra(DatePickeFragment.EXTRA_DATE);
				updateButtonText(date);
			}
			
			break;
		case REQUEST_TIME:
			if(resultCode==Activity.RESULT_OK){
				Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_DATE);
				mCrime.setDate(date);
				updateButtonText(date);
			}
			break;
		case REQUEST_CHOOSE:
			if(resultCode==Activity.RESULT_OK){
				String chooseString = (String)data.getSerializableExtra(ChooseDateTimeFragment.EXTRA_CHOOSE_TYPE);
				popChangeDialog(chooseString);
			}
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
		}
		
	}
	/**
	 * 更新 按钮上的时间 和设置crime 的date对象
	 * @param date
	 */
	private void updateButtonText(Date date){
		mCrime.setDate(date);
		mDateButton.setText(formatDate());
		mTimeButton.setText(DateUtils.formateDate2HourMinute(date, DateUtils.PATTERN_HOUR_MINUTE));
	}
	
	/**
	 * 根据选择的串 弹出对话框 是修改日期还是时间dialog
	 * @param chooseString
	 */
	private void popChangeDialog(String chooseString){
		System.out.println(chooseString);
		if(chooseString.equals(getString(R.string.choose_date_title))){
			FragmentManager fm = getActivity().getSupportFragmentManager();
			DatePickeFragment dialog = DatePickeFragment.getInstance(mCrime.getDate());
			dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);//设置目标fragment，方便参数传递
			dialog.show(fm,DIALOG_DATE);
		}
		else{
			FragmentManager fm = getFragmentManager();
			TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
			dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
			dialog.show(fm, DIALOG_TIME);
		}
	}
	
	@Override
	 public boolean onOptionsItemSelected(MenuItem item){
		int itemId = item.getItemId();
		switch(itemId){
		case android.R.id.home:
			Log.d(TAG, "鼠标点击home 图标");
			/*Intent intent = new Intent(getActivity(), CrimeListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);*/
			if(null!=NavUtils.getParentActivityName(getActivity())){
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
