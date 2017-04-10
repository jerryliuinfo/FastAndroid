package com.apache.fastandroid.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.apache.fastandroid.R;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by jerryliu on 2017/4/10.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMain();
            }
        }, 2000);
    }



    private void toMain(){
        MainActivity.launch(this);
    }
}
