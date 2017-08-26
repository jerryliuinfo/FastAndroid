package com.apache.fastandroid;

import android.os.Bundle;

import com.apache.fastandroid.support.config.ADConfigManager;
import com.apache.fastandroid.support.config.UserConfigManager;
import com.apache.fastandroid.ui.widget.SplashCountDownView;
import com.apache.fastandroid.user.LoginFragment;
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
        if (ADConfigManager.getInstance().isNeedToShowAd()){
            ADConfigManager.getInstance().setLastShowADTime(System.currentTimeMillis());
            coutDownView.setCountDowningText(ResUtil.getString(R.string.splash_countdown_count)).setDuraionn(mDuration)
                    .setCallback(new SplashCountDownView.CountDownCallbackImpl(){
                        @Override
                        public void onFinish(long delay) {
                            jump();
                        }

                        @Override
                        public void onClicked() {
                            jump();
                        }
                    }).start();

        }else {
            jump();
        }
    }

    private void toLogin(){
        LoginFragment.start(this);
    }

    private void toMain(){
        MainActivity.launch(SplashActivity.this);
    }

    private void jump(){
        if (true || UserConfigManager.getInstance().autoLogin())               {
            toMain();
        }else {
            toLogin();
        }
        finish();
    }


}
