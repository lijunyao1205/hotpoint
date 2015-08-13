/**
 * 
 */
package com.hotpot.hellomoon;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 托管一个fragment 里边使用videoView来播放视频资源
 * @author keily
 *
 */
public class HelloMoonVideo2Activity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_moon_video2);
	}
}
