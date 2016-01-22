/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity.absactivity;

import com.bignerdranch.android.criminalintent.R;
import com.bignerdranch.android.criminalintent.activity.CrimeFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * 抽象的 fragmentActivity 用来提供通用fragmentManager 来托管fragment
 * @author keily
 *
 */
public abstract class SingleFragmentActivity extends FragmentActivity {

	/*
	 * 用来生成需要托管的fragment
	 */
	protected abstract Fragment createFragment();
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_fragment);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (null == fragment) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment)
					.commit();
		}
	}
}
