package miraclethings.camvidtest;

import java.util.Timer;
import java.util.TimerTask;

import android.hardware.Camera;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.VideoView;

public class MainActivity extends Activity implements PreviewSurface.Callback {

    private static final String TAG = "camvid";
	private miraclethings.camvidtest.PreviewSurface mSurface;
	private VideoView mVideo;
	private Timer mTimer;
	private boolean mFrontFacing;

	private int mCurrentTimePoint;
	private int[] mTimePoints;
	private int t;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        mSurface = (PreviewSurface) findViewById(R.id.surface);
        mSurface.setCallback(this);
        mSurface.setBackgroundColor(Color.TRANSPARENT);
        
        mVideo = (VideoView) findViewById(R.id.video);

        //String uri = "android.resource://" + getPackageName() + "/" + R.raw.dateapp;
        //mVideo.setVideoURI(Uri.parse(uri));

        mVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
            	mCurrentTimePoint = 0;
                mVideo.start(); //need to make transition seamless.

                //mVideo.seekTo(90000);
                
                doSwitch();
            }
        });
        
        
//        /mVideo.requestFocus();
        
        //mVideo.start();
//        mVideo.seekTo(90000);
        doSwitch(); // first show video
        
        mCurrentTimePoint = 0;
        mTimePoints = new int[8];
        mTimePoints[0] = 0;
        mTimePoints[1] = 20;
        
        mTimePoints[2] = 40;
        mTimePoints[3] = 60;
        
        mTimePoints[4] = 80;
        mTimePoints[5] = 100;
        
        mTimePoints[6] = 120;
        mTimePoints[7] = 150;
        
//        mTimePoints[0] = 2;
//        mTimePoints[1] = 5;
//        mTimePoints[2] = 6;
//        mTimePoints[3] = 9;
//        mTimePoints[4] = 10;
//        mTimePoints[5] = 13;
        
        mFrontFacing = true;
        
        t = 0;
        
        mTimer = new Timer();
        mTimer.schedule(new TimerTask(){
			@Override
			public void run() {
				runOnUiThread(new Runnable(){
					@Override
					public void run() {
						MainActivity.this.checkSwitch();
					}});
			}}, 0, 1000);
    }
	
	// @override
	public void onPause() {
		super.onPause();
		mTimer.cancel();
	}
        
	private void checkSwitch() {
		
		if (mTimePoints[mCurrentTimePoint] < t) {
			mCurrentTimePoint++;
			Log.v(TAG, "SWITCH!!! " + t);
			doSwitch();
		}
		t += 1;
		if (t > 120) {
			t = 0;
			mCurrentTimePoint = 0;
		}
	}
	
    private void doSwitch() {
    	mFrontFacing = !mFrontFacing;
    	mSurface.setVisibility(View.VISIBLE);
    	if (mFrontFacing) 
    		mSurface.switchTo(Camera.CameraInfo.CAMERA_FACING_FRONT);
    	else
    		mSurface.switchTo(Camera.CameraInfo.CAMERA_FACING_BACK);
    	/*
    	if (mVideoShowing) {
    		mSurface.setVisibility(View.INVISIBLE);
    	} else {
    		mSurface.setVisibility(View.VISIBLE);
    	}
    	*/
    }

	@Override
	public void cameraReady() {
		Log.v(TAG, "Camera ready!");
		// Notify feedback class it can use the surface to set the flash
		//mFeedback.setSurface(mSurface);
	}

	@Override
	public void cameraNotAvailable() {
		Log.w(TAG, "Camera not available");
	}    
}
