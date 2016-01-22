package com.example.entity;

import java.util.Date;

public class NewsItem {
	private String title;
	private int resId;
	
	private String source;
	private int count;
	private Date publishTime;
	private int style; //0:∆’Õ® 1:∂‡Õº 2:
	
	
	
	private String[] imgUrl;
	
	private int imgRes[];
	
	public NewsItem(){
		
	}
	public NewsItem(String title, int resId) {
		super();
		this.title = title;
		this.resId = resId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getResId() {
		return resId;
	}
	public void setResId(int resId) {
		this.resId = resId;
	}
	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public int[] getImgRes() {
		return imgRes;
	}
	public void setImgRes(int[] imgRes) {
		this.imgRes = imgRes;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String[] getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String[] imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	
	
}
