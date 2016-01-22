/**
 * 
 */
package com.hotpot.hellomoon;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author keily
 *
 */
public class SurfaceActivity extends Activity implements Callback {

	 /** Called when the activity is first created. */
    MediaPlayer player;
    SurfaceView surface;
    SurfaceHolder surfaceHolder;
    Button play,pause,stop;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_video);
        play=(Button)findViewById(R.id.button1);
        pause=(Button)findViewById(R.id.button2);
        stop=(Button)findViewById(R.id.button3);
        surface=(SurfaceView)findViewById(R.id.surface);
 
        surfaceHolder=surface.getHolder();//SurfaceHolder是SurfaceView的控制接口
        surfaceHolder.addCallback(this); //因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
        surfaceHolder.setFixedSize(320, 220);//显示的分辨率,不设置为视频默认
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//Surface类型
 
        play.setOnClickListener(new OnClickListener(){
             @Override
            public void onClick(View v) {
                player.start();
            }});
        pause.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                player.pause();
            }});
        stop.setOnClickListener(new OnClickListener(){
             @Override
            public void onClick(View v) {
                player.stop();
            }});
    }
 
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
//必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
        player=  MediaPlayer.create(SurfaceActivity.this, R.raw.loop_video);
        player.setDisplay(surfaceHolder);
        //设置显示视频显示在SurfaceView上
            try {
                player.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
 
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
 
    }
 
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if(player.isPlaying()){
        player.stop();
        }
        player.release();
        //Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
    }
}
