package com.example.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.entity.NewsItem;
import com.example.sun_news.R;
import com.example.sun_news.SunNewsApplication;

public class MoreStyleNewsListViewAdapter extends BaseAdapter {

	private Activity mActivity;
	private List<NewsItem> newsList;
	private ImageLoader imageLoader;

	public MoreStyleNewsListViewAdapter(Activity mActivity,List<NewsItem> newsList){
		this.mActivity=mActivity;
		this.newsList=newsList;
		imageLoader=((SunNewsApplication)mActivity.getApplication()).getmImageLoader();
	}
	private final int TYPE_COUNT=3;
	/**
	 * �������������ʾ��������
	 * 0 1 2
	 */
	@Override
	public int getItemViewType(int position) {
		
		// TODO Auto-generated method stub
		return newsList!=null?newsList.get(position).getStyle():-1;
	}
	/**
	 * �������͸���
	 */
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return TYPE_COUNT;
	}



	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.d("jereh","getCount()");
		return newsList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Log.d("jereh","getItem()");
		return newsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		Log.d("jereh","getItemId()");
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		NewsItem item=newsList.get(position);
		if(convertView==null){
			holder=new ViewHolder();
			//��layout.xmlת��ΪView
			switch(item.getStyle()){
			case 0:
				
				convertView=LayoutInflater.from(mActivity).inflate(R.layout.news_item1, null);
				holder.ivImg1=(ImageView)convertView.findViewById(R.id.ivNewsImg);
				holder.ivImg1.setLayoutParams(new RelativeLayout.LayoutParams(100, 100));
				break;
			case 1:
				convertView=LayoutInflater.from(mActivity).inflate(R.layout.news_item2, null);
				holder.ivImg1=(ImageView)convertView.findViewById(R.id.ivImg1);
				holder.ivImg2=(ImageView)convertView.findViewById(R.id.ivImg2);
				holder.ivImg3=(ImageView)convertView.findViewById(R.id.ivImg3);
				break;
			case 2:
				
				convertView=LayoutInflater.from(mActivity).inflate(R.layout.news_item1, null);
				holder.ivImg1=(ImageView)convertView.findViewById(R.id.ivNewsImg);
				holder.ivImg1.setLayoutParams(new RelativeLayout.LayoutParams(100, 100));
				break;
			}
			holder.tvTilte=(TextView)convertView.findViewById(R.id.tvTitle);	
			convertView.setTag(holder);//��¼����ʶ
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		//��uiԪ�ذ�����
		holder.tvTilte.setText(item.getTitle());
		
		
		switch(item.getStyle()){
		case 0:
			imageLoader.get(item.getImgUrl()[0], ImageLoader.getImageListener(holder.ivImg1,
					R.drawable.default_big, R.drawable.default_big));//����ͼƬ���ȴ��ڴ��м��أ��ڴ�û���ٴ��������
			break;
			case 1:
				imageLoader.get(item.getImgUrl()[1], ImageLoader.getImageListener(holder.ivImg2,
						R.drawable.default_big, R.drawable.default_big));//����ͼƬ���ȴ��ڴ��м��أ��ڴ�û���ٴ��������
				imageLoader.get(item.getImgUrl()[2], ImageLoader.getImageListener(holder.ivImg3,
						R.drawable.default_big, R.drawable.default_big));//����ͼƬ���ȴ��ڴ��м��أ��ڴ�û���ٴ��������
				break;
			case 2:
				String imgStr = item.getImgUrl()[0];
				byte[] picByte = Base64.decode(imgStr, Base64.DEFAULT);
				Bitmap bmp = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
				holder.ivImg1.setImageBitmap(bmp);
				break;
		}
		Log.d("jereh","getView()");
		return convertView;
	}
	
	
	private class ViewHolder{
		private TextView tvTilte;
		private ImageView ivImg1;
		private ImageView ivImg2;
		private ImageView ivImg3;	
		
	}

}
