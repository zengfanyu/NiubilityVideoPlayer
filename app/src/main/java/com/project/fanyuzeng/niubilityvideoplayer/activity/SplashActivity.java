package com.project.fanyuzeng.niubilityvideoplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.project.fanyuzeng.niubilityvideoplayer.R;
/**
 * Created by fanyuzeng on 2017/9/21.
 * Function:
 */
public class SplashActivity extends Activity {

    private SharedPreferences mPreferences;
    public static final int GO_HOME = 1;
    public static final int GO_GUIDE = 2;
    public static final int ENTER_DURATION = 2*1000;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_GUIDE:
                    startGuideActivity();
                    break;
                case GO_HOME:
                    startHomeActivity();
                    break;
                default:
                    break;
            }
        }
    };

    private void startHomeActivity() {
        Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();

    }

    private void startGuideActivity() {
        Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPreferences = getSharedPreferences("config", MODE_PRIVATE);
        init();
    }

    private void init() {
        boolean isFirstIn = mPreferences.getBoolean("mIsFirstIn", true);
        if (isFirstIn) {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE,ENTER_DURATION);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_HOME,ENTER_DURATION);
        }
    }
}
