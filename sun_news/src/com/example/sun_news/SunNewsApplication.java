package com.example.sun_news;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.tools.BitmapCache;

import android.app.Application;


public class SunNewsApplication extends Application {
	private ImageLoader mImageLoader;
	private RequestQueue mRequestQueue;
	@Override
	public void onCreate(){
		mRequestQueue=Volley.newRequestQueue(this);//创建RequestQueue，可发送异步请求
		mImageLoader=new ImageLoader(mRequestQueue,new BitmapCache());//创建ImageLoader,用于将图片存入缓存和从缓存中取出图片
	}
	public ImageLoader getmImageLoader() {
		return mImageLoader;
	}
	public RequestQueue getmRequestQueue() {
		return mRequestQueue;
	}
	
}
