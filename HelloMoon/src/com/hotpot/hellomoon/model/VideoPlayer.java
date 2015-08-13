/**
 * 
 */
package com.hotpot.hellomoon.model;

import android.content.Context;
import android.media.MediaPlayer;

import com.hotpot.hellomoon.R;

/**
 * 视频播放
 * @author keily
 *
 */
public class VideoPlayer {

	private  MediaPlayer mMediaPlayer;
	private Context c;
	
	public VideoPlayer(Context c){
		this.c = c;
		mMediaPlayer = MediaPlayer.create(c, R.raw.loop_video);
		mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				stop();
			}
		}); 
		
	}
	
	public void stop(){
		if(null!=mMediaPlayer){
			mMediaPlayer.release();
			mMediaPlayer = null;
			/*mMediaPlayer.pause();
			mMediaPlayer.seekTo(0);*/
		}
	}
	
	public void release(){
		if(null!=mMediaPlayer){
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
	
	public void start(Context c){
		/*mMediaPlayer.seekTo(0);
		mMediaPlayer.start();*/
		stop();
		if(null==mMediaPlayer){
			mMediaPlayer = MediaPlayer.create(c, R.raw.loop_video);
			mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					stop();
				}
			});
		}
		mMediaPlayer.start();
	}
	
	public void pause(){
		if(null!=mMediaPlayer){
			if(mMediaPlayer.isPlaying()){
				mMediaPlayer.pause();
			}
			else{
				mMediaPlayer.start();
			}
		}
	}
	
	
	public MediaPlayer getMdeiaPlayer(){
		return mMediaPlayer;
	}
}
