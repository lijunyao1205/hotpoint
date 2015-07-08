/**
 * 
 */
package com.bignerdranch.android.criminalintent.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;

/**
 * 存放陋习crime模型的容器
 * 
 * @author keily
 * 
 */
public class CrimeLib {

	private static CrimeLib sInstance;
	private Context mContext;
	private List<Crime> mCrimes;

	private CrimeLib(Context context) {
		this.mContext = context;
		mCrimes = new ArrayList<Crime>();
		for(int i=0;i<100;i++){
			Crime crime = new Crime();
			crime.setTitle("Crime #"+i);
			crime.setSoved(i%2==0);
			mCrimes.add(crime);
		}
	}

	public static CrimeLib getInstance(Context context) {
		if (null == sInstance) {
			sInstance = new CrimeLib(context.getApplicationContext());
		}
		return sInstance;
	}

	public List<Crime> getCrimes() {
		return mCrimes;
	}
	
	public Crime getCrime(UUID uid){
		if(null!=mCrimes)
		for(Crime crime:mCrimes){
			if(uid.equals(crime.getId())){
				return crime;
			}
		}
		return null;
	}
	
	
}
