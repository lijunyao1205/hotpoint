/**
 * 
 */
package com.hotpot.hellomoon.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hotpot.hellomoon.R;

/**
 *  进行视频播放
 * @author keily
 *
 */
public class HelloMoonVideo2Fragment extends Fragment {

	private VideoView mVideoView;
	private MediaController mController;
	private Uri uri ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.fragment_hello_moon_video2, container, false);
		mVideoView = (VideoView)view.findViewById(R.id.hello_moon_videoView);
		uri = Uri.parse("android.resource://"+"com.hotpot.hellomoon/raw/loop_video");
		mController = new MediaController(getActivity());
		mVideoView.setVideoURI(uri);
		mVideoView.setMediaController(mController);
		
		mController.setMediaPlayer(mVideoView);
//		mController.setAnchorView(mVideoView);
		mController.show(0);
		
		mVideoView.requestFocus();
		mVideoView.start();
		
		return view;
	}
}
