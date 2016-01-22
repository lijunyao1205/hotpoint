package com.example.tools;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache{

	private LruCache<String, Bitmap> mCache;
	public BitmapCache(){
		// ��ȡ�������ڴ�����ֵ��ʹ���ڴ泬�����ֵ������OutOfMemory�쳣
		//int maxSize=5*1024*1024;
		 // ʹ���������ڴ�ֵ��1/8��Ϊ����Ĵ�С��
		int maxsize=(int)(Runtime.getRuntime().maxMemory()/1024)/8;
		mCache=new LruCache<String, Bitmap>(maxsize){
			  // ��д�˷���������ÿ��ͼƬ�Ĵ�С��Ĭ�Ϸ���ͼƬ����
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return super.sizeOf(key, value);
			}
			
		};
	}
	/**
	 * ��ȡ������ͼƬ
	 */
	@Override
	public Bitmap getBitmap(String key) {
		// TODO Auto-generated method stub
		return mCache.get(key);
	}
	/**
	 * ���뻺��
	 */
	@Override
	public void putBitmap(String key, Bitmap bitmap) {
		// TODO Auto-generated method stub
		if(bitmap!=null)mCache.put(key, bitmap);
	}

}
