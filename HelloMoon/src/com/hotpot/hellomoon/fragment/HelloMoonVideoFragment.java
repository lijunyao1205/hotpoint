/**
 * 
 */
package com.hotpot.hellomoon.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hotpot.hellomoon.R;
import com.hotpot.hellomoon.model.VideoPlayer;

/**
 * 视频播放 frament
 * @author keily
 *
 */
public class HelloMoonVideoFragment extends Fragment {

	private Button mPlayButton;
	private Button mPauseButton;
	private Button mStopButton;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private VideoPlayer mVideoPlayer;
	private MediaPlayer mMediaPlayer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View  view = (View)inflater.inflate(R.layout.fragment_hello_moon_video, container, false);
		mVideoPlayer = new VideoPlayer(getActivity());
		mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.loop_video);
		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mMediaPlayer.release();
				mMediaPlayer = null;
				getActivity().finish();
			}
		});  
		mPlayButton = (Button)view.findViewById(R.id.hello_moon_playVideo_button);
		mPlayButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(null!=mMediaPlayer){
					mMediaPlayer.start();
				}
			}
		});
		
		mPauseButton = (Button)view.findViewById(R.id.hello_moon_pauseVideo_button);
		mPauseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=mMediaPlayer){
					if(mMediaPlayer.isPlaying()){
						mMediaPlayer.pause();
					}
					else{
						mMediaPlayer.start();
					}
				}
			}
		});
		
		mStopButton = (Button)view.findViewById(R.id.hello_moon_stopVideo_button);
		mStopButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=mMediaPlayer){
					mMediaPlayer.release();
					mMediaPlayer = null;
					getActivity().finish();
				}
			}
		});
		mSurfaceView =(SurfaceView)view.findViewById(R.id.hello_moon_surfaceView);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				mMediaPlayer.setDisplay(holder);//设置视频播放的holder
			}
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				
			}
		});
		
		return view;
	}
	
	@Override
	 public void onDestroy(){
		super.onDestroy();
		if(null!=mMediaPlayer){
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
	  
}
