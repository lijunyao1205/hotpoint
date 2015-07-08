/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import com.bignerdranch.android.criminalintent.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * 托管一个日期组件
 * 
 * @author keily
 * 
 */
public class DatePickeFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog dialog = new AlertDialog.Builder(getActivity()).setTitle(
				R.string.date_picker_title).setPositiveButton(
				android.R.string.ok, null).create();
		return dialog;
	}
}
