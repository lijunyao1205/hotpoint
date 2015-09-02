/**
 * 
 */
package com.bignerdranch.android.criminalintent.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bignerdranch.android.criminalintent.activity.serialize.CriminalIntentJSONSerializer;

import android.content.Context;
import android.util.Log;

/**
 * 存放陋习crime模型的容器
 * 
 * @author keily
 * 
 */
public class CrimeLib {

	private static final String TAG = "CrimeLib";
	private static CrimeLib sInstance;
	private Context mContext;
	private List<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerilizer;
	private static final String FILE_NAME = "crime.json";

	private CrimeLib(Context context) {
		this.mContext = context;
		mSerilizer = new CriminalIntentJSONSerializer(context, FILE_NAME);
		try {
			Log.d(TAG, "加载crime数据文件");
//			mCrimes = mSerilizer.loadCrimes();
			mCrimes = mSerilizer.loadCrimesFromSdCard();
		} catch (Exception e) {
			Log.d(TAG, "加载crimes数据文件发生异常", e);
			mCrimes = new ArrayList<Crime>();//如果不初始化一个实例list ，listfragment就初始化列表报错，arrayadapter 空数据
		}
		/*for(int i=0;i<100;i++){
			Crime crime = new Crime();
			crime.setTitle("Crime #"+i);
			crime.setSoved(i%2==0);
			mCrimes.add(crime);
		}*/
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
	/**
	 * 新增crime对象
	 * @param crime
	 */
	public void addCrime(Crime crime){
		mCrimes.add(crime);
	}
	/**
	 * 保存crimes
	 */
	public boolean saveCrimes(){
		Log.d(TAG, "saveCrimes()");
		try {
//			mSerilizer.saveCrimes(mCrimes);
			mSerilizer.saveCrimesOnSdCard(mCrimes);
		} catch (Exception e) {
			Log.d(TAG, "saveCrimes() found exception",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 删除crime对象
	 * @param crime
	 */
	public void delete(Crime crime){
		if(null!=mCrimes&&0<mCrimes.size()){
			mCrimes.remove(crime);
		}
	}
}
