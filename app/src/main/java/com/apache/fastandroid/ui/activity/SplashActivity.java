package com.apache.fastandroid.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.config.PublishVersionManager;
import com.tesla.framework.common.util.DebugUtils;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by jerryliu on 2017/4/10.
 */

public class SplashActivity extends BaseActivity {
    @ViewInject(id = R.id.lay_bg)
    View layBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(layBg,"scaleX", 1, 1.2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(layBg,"scaleY", 1, 1.2f);
        animatorSet.setDuration(5000);
        animatorSet.playTogether(scaleX,scaleY);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                toMain();
            }
        });
        animatorSet.start();

        PublishVersionManager.isTest();
        NLog.d("IsTest = %s, channel = %s, channelId = %s", PublishVersionManager.isTest(),PublishVersionManager.getChannel(),PublishVersionManager.getChannelId());

        NLog.d(NLog.TAG, "debug util = %s", DebugUtils.isDebug());

    }



    private void toMain(){
        MainActivity.launch(this);
        finish();
    }
}
