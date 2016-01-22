/**
 * 
 */
package com.hotpot.hellomoon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hotpot.hellomoon.HelloMoonVideo2Activity;
import com.hotpot.hellomoon.HelloMoonVideoActivity;
import com.hotpot.hellomoon.R;
import com.hotpot.hellomoon.model.AudioPlayer;

/**
 * fragment 承载图片背景和两个按钮
 * @author keily
 *
 */
public class HelloMoonFragment extends Fragment {

	private Button mPlayButton;
	private Button mPauseButton;
	private Button mStopButton;
	private AudioPlayer mPalyer;
	private Button mGotoVideoButton;
	private Button mTwoGotoVideoButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setRetainInstance(true);//保留fragment实例
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_hello_moon, container, false);
		mPalyer = new AudioPlayer();
		mPlayButton = (Button)view.findViewById(R.id.hellomoon_playButton);
		mPlayButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=mPalyer){
					mPalyer.play(getActivity());
				}
			}
		});
		
		mPauseButton = (Button)view.findViewById(R.id.hellomoon_pauseButton);
		mPauseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPalyer.pause();
			}
		});
		mStopButton = (Button)view.findViewById(R.id.hellomoon_stopButton);
		mStopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=mPalyer){
					mPalyer.stop();
				}
			}
		});
		
		mGotoVideoButton = (Button)view.findViewById(R.id.hellomoon_gotoPlayVideoButton);
		mGotoVideoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HelloMoonVideoActivity.class);
				startActivity(intent);
			}
		});
		
		mTwoGotoVideoButton = (Button)view.findViewById(R.id.hellomoon_twoGotoPlayVideoButton);
		mTwoGotoVideoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),HelloMoonVideo2Activity.class);
				startActivity(intent);
			}
		});
		
		
		return view;
	}
	
	@Override
	 public void onDestroy(){
		super.onDestroy();
		mPalyer.stop();
	}
}
