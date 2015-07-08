/**
 * 
 */
package com.bignerdranch.android.criminalintent.model;

import java.util.Date;
import java.util.UUID;

/**
 * 陋习模型类
 * 
 * @author keily
 * 
 */
public class Crime {

	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSoved;

	public Crime() {
		mId = UUID.randomUUID();
		mDate = new Date();
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

}
