package com.apache.fastandroid.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.config.CommonConfigManager;
import com.apache.fastandroid.ui.widget.SplashCountDownView;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by jerryliu on 2017/4/10.
 */

public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();
    @ViewInject(id = R.id.splash_coutdown_view)
    private SplashCountDownView coutDownView;


    private int mDuration = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (CommonConfigManager.getInstance().isFirstSplash()){
            coutDownView.setCountDowningText(ResUtil.getString(R.string.splash_countdown_count)).setDuraionn(mDuration).setCallback(new SplashCountDownView.ICountDownCallback() {
                @Override
                public void onStart() {

                }

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish(long delay) {
                    toMain(delay);
                }

                @Override
                public void onClicked() {
                    toMain(0);
                }
            }).start();
        }else {
            toMain(0);
        }

    }







    private void toMain(long delay){
        if (CommonConfigManager.getInstance().isFirstSplash()){
            CommonConfigManager.getInstance().setFirstSplash(false);
        }
        if (delay > 0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MainActivity.launch(SplashActivity.this);
                    //DatabindingActivivity.start(SplashActivity.this);
                    finish();
                }
            },delay);
        }else {
            MainActivity.launch(SplashActivity.this);
            //DatabindingActivivity.start(SplashActivity.this);
            finish();
        }
    }
}
