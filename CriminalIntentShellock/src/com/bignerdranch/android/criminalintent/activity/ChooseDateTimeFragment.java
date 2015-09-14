/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.bignerdranch.android.criminalintent.R;

/**
 * 选择修改的是时间 或者日期对话框
 * @author keily
 *
 */
public class ChooseDateTimeFragment extends SherlockDialogFragment {
	private static ChooseDateTimeFragment instance;
	private RadioGroup mRadioGroup;
	private String checkString;
	public static String EXTRA_CHOOSE_TYPE = "com.bignerdranch.android.criminalintent.activity.ChooseDateTimeFragment.EXTRA_CHOOSE_TYPE";
	
	public static ChooseDateTimeFragment newInstance(){
		if(null== instance){
			instance = new ChooseDateTimeFragment();
		}
		return instance;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle onSavedInstance){
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_choose_datetime, null);
		mRadioGroup = (RadioGroup)view.findViewById(R.id.choose_date_time_radio);
		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
				checkString = radioButton.getText().toString();
			}
		});
		
		AlertDialog chooseDialog = new AlertDialog.Builder(getActivity())
			.setTitle(R.string.choose_crime_date_change_title)
			.setView(view)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					setResult(Activity.RESULT_OK);
				}
			}).create();
		
		return chooseDialog;
	}
	
	private void setResult(int resultCode){
		if(null==getTargetFragment())
			return ;
		Intent intent = new Intent();
		intent.putExtra(EXTRA_CHOOSE_TYPE, checkString);
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
	}
}
