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
		mRequestQueue=Volley.newRequestQueue(this);//����RequestQueue���ɷ����첽����
		mImageLoader=new ImageLoader(mRequestQueue,new BitmapCache());//����ImageLoader,���ڽ�ͼƬ���뻺��ʹӻ�����ȡ��ͼƬ
	}
	public ImageLoader getmImageLoader() {
		return mImageLoader;
	}
	public RequestQueue getmRequestQueue() {
		return mRequestQueue;
	}
	
}
