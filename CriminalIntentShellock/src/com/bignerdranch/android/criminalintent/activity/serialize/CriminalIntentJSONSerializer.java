/**
 * 
 */
package com.bignerdranch.android.criminalintent.activity.serialize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONTokener;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.bignerdranch.android.criminalintent.model.Crime;

/**
 * 序列化 crimeLib 中crimes数据 到沙盒 目录，存储到文件中去
 * @author keily
 *
 */
public class CriminalIntentJSONSerializer {

	private Context mContext;
	private String mFileName;
	
	public CriminalIntentJSONSerializer(Context c,String f){
		mContext = c;
		mFileName = f;
	}
	
	/**
	 * 将数据存储在 手机内部存储上
	 * @param crimes
	 * @throws Exception
	 */
	public void saveCrimes(List<Crime> crimes) throws Exception{
		if(null!=crimes){
			JSONArray array = new JSONArray();
			for(Crime crime:crimes){
				array.put(crime.toJson());//存放JSONObject 对象
			}
			
			Writer writer = null;
			try {
				FileOutputStream out =mContext.openFileOutput(mFileName,Context.MODE_PRIVATE);
				writer = new OutputStreamWriter(out); 
				writer.write(array.toString());
			} 
			finally{
				if(null!=writer){
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 将 数据存储在 sdCard上
	 * @param crimes
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void saveCrimesOnSdCard(List<Crime> crimes) throws Exception{
		if(null!=crimes){
			JSONArray array = new JSONArray();
			for(Crime crime:crimes){
				array.put(crime.toJson());//存放JSONObject 对象
			}
			
			if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//外部存储没有
				saveCrimes(crimes);
				return;
			}
			Writer writer = null;
			try {
				File parent = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
				parent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				File newFile = new File(parent, mFileName);
				if(!newFile.exists()){
					newFile.createNewFile();
				}
				FileOutputStream out =new FileOutputStream(newFile);
				writer = new OutputStreamWriter(out); 
				writer.write(array.toString());
			} 
			finally{
				if(null!=writer){
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public List<Crime> loadCrimes() throws Exception{
		BufferedReader reader = null;
		List<Crime> crimes = new ArrayList<Crime>();
		try {
			FileInputStream fis = mContext.openFileInput(mFileName);
			reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while((line =reader.readLine())!=null){
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
			if(null!=array){
				for(int i=0;i<array.length();i++){
					crimes.add(new Crime(array.getJSONObject(i)));
				}
			}
		}catch(FileNotFoundException e){
			//忽略异常， 首次加载是，文件是不存在的
		} 
		finally {
			if(null!=reader)
				reader.close();
		}
		return crimes;
	}
	
	/**
	 * 将数据从sdCard 加载出来
	 * @return
	 * @throws Exception
	 */
	public List<Crime> loadCrimesFromSdCard() throws Exception{
		BufferedReader reader = null;
		List<Crime> crimes = new ArrayList<Crime>();
		
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return loadCrimes();
		}
		try {
			File parent =mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);//返回内置sd卡的 /data;/应用目录 ，需要在manifest中配置读写权限
			parent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//返回内置sd卡 pictures目录
			File newFile = new File(parent, mFileName);
			FileInputStream fis = new FileInputStream(newFile);
			reader = new BufferedReader(new InputStreamReader(fis));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while((line =reader.readLine())!=null){
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
			if(null!=array){
				for(int i=0;i<array.length();i++){
					crimes.add(new Crime(array.getJSONObject(i)));
				}
			}
		}catch(FileNotFoundException e){
			//忽略异常， 首次加载是，文件是不存在的
		} 
		finally {
			if(null!=reader)
				reader.close();
		}
		return crimes;
	}
}
