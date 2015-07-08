/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity;

import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.model.Crime;
import com.bignerdranch.android.criminalintent.model.CrimeLib;

/**
 * 支持左划 ，右划的paper activity
 * 
 * @author keily
 * 
 */
public class CrimePaperActivity extends FragmentActivity {

	private static final String TAG = "CrimePaperActivity";
	private ViewPager mViewPager;
	private List<Crime> mCrimes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(CrimePaperActivity.this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		mCrimes = CrimeLib.getInstance(CrimePaperActivity.this).getCrimes();

		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

			@Override
			public int getCount() {
				return mCrimes.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				Crime crime = mCrimes.get(arg0);
				return CrimeFragment.getInstance(crime.getId());
			}
		});

		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					/**
					 * 当页面滑动时，修改操作栏标题为crime 的titile
					 */
					public void onPageSelected(int position) {
						Crime crime = mCrimes.get(position);
						String title = crime.getTitle();
						setTitle(title);
					}

					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					public void onPageScrollStateChanged(int arg0) {

					}
				});

		Intent intent = getIntent();
		UUID crimeId = (UUID) intent
				.getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		int index = 0;
		for (Crime crime : mCrimes) {
			if (crimeId.equals(crime.getId())) {
				break;
			}
			index++;
		}
		mViewPager.setCurrentItem(index);
		mViewPager.setOffscreenPageLimit(5);

	}
}
