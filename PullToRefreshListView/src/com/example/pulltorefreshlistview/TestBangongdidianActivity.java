/**
 * 
 */
package com.example.pulltorefreshlistview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 测试
 * @author keily
 *
 */
public class TestBangongdidianActivity extends MyListActivity<Map<String,Object>> {

	private MyAdapter adapter = null;//列表适配器
	public static final String[] DATA_COLUM_NAME =new String[]{"orgqc","orgaddr","orgdh","orgbgsj","orgid","orgmc"};//数据列集合
	
	@Override
	protected List<Map<String, Object>> getListModel(
			Map<String, String> paramModel) {
		List<Map<String,Object>> list = getOrganizationListByUpId(paramModel);
		return list;
	}

	@Override
	protected Map<String, String> generateModelByIntent() {
		Bundle bundle = this.getIntent().getExtras();
		String title = null;
		String orgId = null;
		String orgQc = null;
		if(null!=bundle){
			title = bundle.getString("title");
			orgId = bundle.getString(TestBangongdidianActivity.DATA_COLUM_NAME[4]);//orgid
			orgQc = bundle.getString(TestBangongdidianActivity.DATA_COLUM_NAME[0]);//orgqc
		}
		if(null==orgId){
			orgId = "1754";//银川市公安局
		}
		Map<String, String> paramMap = new HashMap<String, String>(1);
		paramMap.put("orgid", orgId);
		paramMap.put("pageCount", String.valueOf(pageCount));
		paramMap.put("pageSize", String.valueOf(pageSize));
		return paramMap;
	}

	@Override
	protected void gotoDetail(Map<String, Object> model) {
		
		
	}

	@Override
	protected MyAdapter generateListAdapter() {
		if(null==adapter){
			adapter = new MyAdapter(TestBangongdidianActivity.this);
		}
		return adapter;
	}
	
	/**
	 * 根据上级办公地点id 获取下级办公地点列表
	 * @param orgId
	 * @return
	 */
	private List<Map<String,Object>> getOrganizationListByUpId(Map<String,String> map){
		ArrayList<Map<String,Object>> list = null;
		/*Map<String,String> map = new HashMap<String, String>(1);//空参数map
		map.put("orgid", orgId);*/
		JSONObject json= HttpPostUtil.getJSONObject(getString(R.string.getBgddListByUpNodeIdUrl), map);
		if(null!=json){
			if(json.optBoolean("flat", false)){
				String listParmaString = json.optString("listParam");
				JSONArray jsonArry = json.optJSONArray("listParam");//测试是否直接获取到array
				if(null!=listParmaString&&!"".equals(listParmaString)){
					try {
						JSONArray jsonAry = new JSONArray(listParmaString);
						list = new ArrayList<Map<String,Object>>(jsonAry.length());
						HashMap<String,Object> _map = null;
						for(int i=0;i<jsonAry.length();i++){
							JSONObject jsonObj = jsonAry.getJSONObject(i);
							String _orgId = jsonObj.optString(TestBangongdidianActivity.DATA_COLUM_NAME[4]);
							String orgqc = jsonObj.optString(TestBangongdidianActivity.DATA_COLUM_NAME[0]);
							String orgaddr = jsonObj.optString(TestBangongdidianActivity.DATA_COLUM_NAME[1]);
							String orgdh = jsonObj.optString(TestBangongdidianActivity.DATA_COLUM_NAME[2]);
							String orgbgsj = jsonObj.optString(TestBangongdidianActivity.DATA_COLUM_NAME[3]);
							String orgmc = jsonObj.optString(TestBangongdidianActivity.DATA_COLUM_NAME[5]);
							
							if(null==orgqc||"".equals(orgqc)||"null".equals(orgqc)){
								if(null!=orgmc&&!"null".equals(orgmc))
									orgqc = orgmc;
							}
							_map = new HashMap<String, Object>(6);
							_map.put(TestBangongdidianActivity.DATA_COLUM_NAME[0], (null!=orgqc&&!"null".equals(orgqc)?orgqc:""));
							_map.put(TestBangongdidianActivity.DATA_COLUM_NAME[1], (null!=orgaddr&&!"null".equals(orgaddr)?orgaddr:""));
							_map.put(TestBangongdidianActivity.DATA_COLUM_NAME[2], (null!=orgdh&&!"null".equals(orgdh)?orgdh:""));
							_map.put(TestBangongdidianActivity.DATA_COLUM_NAME[3], (null!=orgbgsj&&!"null".equals(orgbgsj)?orgbgsj:""));
							_map.put(TestBangongdidianActivity.DATA_COLUM_NAME[4], (null!=_orgId&&!"null".equals(_orgId)?_orgId:""));
							_map.put(TestBangongdidianActivity.DATA_COLUM_NAME[5], (null!=orgmc&&!"null".equals(orgmc)?orgmc:""));
							list.add(_map);
						}
					} catch (JSONException e) {
						e.printStackTrace();
						return list;
					}
				}
			}
		}
		return list;
	}
	
	private class MyAdapter extends MyListAdapter<Map<String,Object>>{

		public MyAdapter(Context mContext) {
			super(mContext);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = null;
			ViewHolder holder = null;
			if(null==convertView){
				view = (View)mInflater.inflate(R.layout.list_bangongdidian_mx, parent, false);
				holder = new ViewHolder();
				holder.setmMcTextView((TextView)view.findViewById(R.id.bangongdidian_textview_mc));
				view.setTag(holder);
			}
			else{
				view = convertView;
				holder = (ViewHolder)view.getTag();
			}
			
			Map<String, Object> map = modelList.get(position);
			String _orgQc = (null!=map.get(TestBangongdidianActivity.DATA_COLUM_NAME[0])?map.get(TestBangongdidianActivity.DATA_COLUM_NAME[0]).toString():"");
			holder.getmMcTextView().setText(_orgQc);
			return view;
		}
		
	}
	
	private static class ViewHolder{
		private TextView mMcTextView = null;

		public void setmMcTextView(TextView mMcTextView) {
			this.mMcTextView = mMcTextView;
		}

		public TextView getmMcTextView() {
			return mMcTextView;
		}
		
	}

}
