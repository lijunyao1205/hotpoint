/**
 * 
 */
package com.hotpot.hellomoon.model;

import android.content.Context;
import android.media.MediaPlayer;

import com.hotpot.hellomoon.R;

/**
 * 封装一个视频播放工具类
 * @author keily
 *
 */
public class AudioPlayer {

	private MediaPlayer mPlayer;
	
	/**
	 * 视频停止
	 */
	public void stop(){
		if(null!=mPlayer){
			mPlayer.release();
			mPlayer = null;
		}
	}
	
	/**
	 * 视频播放方法
	 * @param c
	 */
	public void play(Context c){
		stop();
		mPlayer = MediaPlayer.create(c, R.raw.one_small_step);
		mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				stop();	
			}
		});
		mPlayer.start();
	}
	
	/**
	 * 暂停/继续播放音频
	 */
	public void pause(){
		if(null!=mPlayer){
			boolean isPlay = mPlayer.isPlaying();
			if(isPlay){
				mPlayer.pause();
			}
			else{
				mPlayer.start();
			}
		}
	}
	
}
