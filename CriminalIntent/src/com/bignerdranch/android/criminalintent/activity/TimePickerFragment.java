/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TimePicker;

import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.util.DateUtils;

/**
 * 时间 dialog
 * @author keily
 *
 */
public class TimePickerFragment extends DialogFragment {

	private static TimePickerFragment instance;
	public static String EXTRA_DATE = "com.bignerdranch.android.criminalintent.activity.TimePickerFragment.EXTRA_DATE";
	private Date mDate ;
	
	public static TimePickerFragment newInstance(Date date){
		instance = new TimePickerFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(EXTRA_DATE, date);
		instance.setArguments(bundle);
		
		return instance;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
		TimePicker timePicker = (TimePicker)view.findViewById(R.id.dialog_date_timePicker);
		timePicker.setIs24HourView(true);
		if(null!=mDate){
			timePicker.setCurrentHour(DateUtils.getHour24ByDate(mDate));
			timePicker.setCurrentMinute(DateUtils.getMinuteByDate(mDate));
			timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
				
				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
					mDate = DateUtils.getDateByHourAndMinute(mDate, hourOfDay, minute);
					getArguments().putSerializable(EXTRA_DATE, mDate);//保存参数，当屏幕旋转时获取该参数进行初始化
				}
			});
		}
		AlertDialog dialog = new AlertDialog.Builder(getActivity())
		.setTitle(R.string.time_picker_title)
		.setView(view)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setResult(Activity.RESULT_OK);
			}
		}).create();
		
		return dialog;
	}
	
	private void setResult(int resultCode){
		if(null==getTargetFragment())
			return ;
		Intent intent = new Intent();
		intent.putExtra(EXTRA_DATE, mDate);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
	}
}
