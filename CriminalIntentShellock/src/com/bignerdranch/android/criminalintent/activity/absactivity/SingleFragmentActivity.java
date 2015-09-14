/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity.absactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.bignerdranch.android.criminalintent.R;

/**
 * @author keily
 *
 */
public abstract class SingleFragmentActivity extends SherlockFragmentActivity {

	/*
	 * 用来生成需要托管的fragment
	 */
	protected abstract Fragment createFragment();
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_fragment);
		FragmentManager fm = getSupportFragmentManager();getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (null == fragment) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment)
					.commit();
		}
	}
	
}
