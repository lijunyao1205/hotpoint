/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import android.support.v4.app.Fragment;

import com.bignerdranch.android.criminalintent.activity.absactivity.SingleFragmentActivity;

/**
 * 用来托管crimeListFragment
 * @author keily
 *
 */
public class CrimeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new CrimeListFragment();
	}

}
