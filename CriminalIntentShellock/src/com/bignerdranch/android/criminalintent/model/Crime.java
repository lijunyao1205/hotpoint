/**
 * 
 */
package com.bignerdranch.android.criminalintent.model;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.JsonReader;

/**
 * 陋习模型类
 * 
 * @author keily
 * 
 */
public class Crime {

	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_DATE = "date";
	private static final String JSON_SOVED = "soved";
	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSoved;

	public Crime() {
		mId = UUID.randomUUID();
		mDate = new Date();
	}
	
	public Crime(JSONObject json) throws JSONException{
		mId = UUID.fromString(json.getString(JSON_ID));
		if(json.has(JSON_TITLE)){
			mTitle = json.getString(JSON_TITLE);
		}
		mDate = new Date(json.getLong(JSON_DATE));
		mSoved = json.getBoolean(JSON_SOVED);
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public UUID getId() {
		return mId;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public boolean isSoved() {
		return mSoved;
	}

	public void setSoved(boolean soved) {
		mSoved = soved;
	}
	
	@Override
	public String toString(){
		return mTitle;
	}
	
	public JSONObject toJson(){
		JSONObject json = new JSONObject();
		try {
			json.put(JSON_ID, mId.toString());
			json.put(JSON_TITLE, mTitle);
			json.put(JSON_DATE, mDate.getTime());//
			json.put(JSON_SOVED, mSoved);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

}
