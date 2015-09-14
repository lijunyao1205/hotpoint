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
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.util.DateUtils;

/**
 * 托管一个日期组件
 * @author keily
 *
 */
public class DatePickeFragment extends SherlockDialogFragment {

	private static DatePickeFragment sDatePickeFragment;
	public static String EXTRA_DATE = "com.bignerdranch.android.criminalintent.activity.DatePickeFragment.date";
	private Date mDate;

	public static DatePickeFragment getInstance(Date date) {
		sDatePickeFragment = new DatePickeFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(EXTRA_DATE, date);
		sDatePickeFragment.setArguments(bundle);
		return sDatePickeFragment;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		mDate = (Date) bundle.getSerializable(EXTRA_DATE);

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_date, null);
		DatePicker datePicker = (DatePicker) view
				.findViewById(R.id.dialog_date_datePicker);
		TimePicker timePicker = (TimePicker) view
				.findViewById(R.id.dialog_date_timePicker);
		if(null!=mDate){	
		datePicker.init(DateUtils.getYearByDate(mDate),
					DateUtils.getMonthByDate(mDate),
					DateUtils.getDayByDate(mDate),
					new DatePicker.OnDateChangedListener() {

						@Override
						public void onDateChanged(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							mDate = DateUtils.getDateByYearMonthDay(mDate, year, monthOfYear, dayOfMonth);
							getArguments().putSerializable(EXTRA_DATE, mDate);// 保存参数，当屏幕旋转时，可以恢复选择的时间
						}
					});

			timePicker.setIs24HourView(true);
			timePicker.setCurrentHour(DateUtils.getHour24ByDate(mDate));
			timePicker.setCurrentMinute(DateUtils.getMinuteByDate(mDate));
			timePicker
					.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

						@Override
						public void onTimeChanged(TimePicker view,
								int hourOfDay, int minute) {
							mDate = DateUtils.getDateByHourAndMinute(mDate, hourOfDay, minute);
							getArguments().putSerializable(EXTRA_DATE, mDate);// 保存参数，当屏幕旋转时，可以恢复选择的时间
						}
					});
		}
		AlertDialog dialog = new AlertDialog.Builder(getActivity())
				.setTitle(R.string.date_picker_title)
				.setView(view)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								setResult(Activity.RESULT_OK);
							}

						}).create();
		return dialog;
	}

	/**
	 * 返回保存的信息
	 * 
	 * @param resultCode
	 *            结果代码
	 */
	private void setResult(int resultCode) {
		if (getTargetFragment() == null)
			return;
		Intent intent = new Intent();
		intent.putExtra(EXTRA_DATE, mDate);
		getTargetFragment().onActivityResult(getTargetRequestCode(),
				resultCode, intent);
	}
}
