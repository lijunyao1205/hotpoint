/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;

import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.model.CrimeLib;

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
	private CheckBox mSovledCheckBox;
	private static SimpleDateFormat JDATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss ms", Locale.CHINA);
	private static DateFormat ADATE_FORMAT = new DateFormat();
	private static String DIALOG_DATE = "date";
	
	public static CrimeFragment getInstance(UUID crimeID){
		sCrimeFragment = new CrimeFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(EXTRA_CRIME_ID, crimeID);
		sCrimeFragment.setArguments(bundle);
		return sCrimeFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "CrimeFragment onCreate()");
		super.onCreate(savedInstanceState);
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
		StringBuilder pattern = new StringBuilder();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
		calendar.setTime(mCrime.getDate());
		String week = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.CHINA);
		pattern.append(week).append(",");
		String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CHINA);
		pattern.append(month).append(" ");
		pattern.append(calendar.get(Calendar.DAY_OF_MONTH)).append(",");
		pattern.append(calendar.get(Calendar.YEAR));
		mDateButton.setText(pattern);
		mDateButton.setEnabled(true);
		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePickeFragment dialog = new DatePickeFragment();
				dialog.show(fm,DIALOG_DATE);
			}
		});

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
}
