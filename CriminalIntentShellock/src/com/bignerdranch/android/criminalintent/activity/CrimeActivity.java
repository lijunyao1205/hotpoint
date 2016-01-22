/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import java.util.UUID;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.bignerdranch.android.criminalintent.activity.absactivity.SingleFragmentActivity;

/**
 * @author keily
 *
 */
public class CrimeActivity extends SingleFragmentActivity {

	/* (non-Javadoc)
	 * @see com.bignerdranch.android.criminalintent.activity.absactivity.SingleFragmentActivity#createFragment()
	 */
	@Override
	protected Fragment createFragment() {
		Intent intent = getIntent();
		UUID crimeId = (UUID)intent.getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		
		return CrimeFragment.getInstance(crimeId);
	}

}
