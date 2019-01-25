package com.apache.fastandroid;

import android.os.Bundle;
import android.view.View;
import com.apache.fastandroid.app.AppContext;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.support.config.ADConfigManager;
import com.apache.fastandroid.widget.SplashCountDownView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        coutDownView.setVisibility(View.VISIBLE);
        ADConfigManager.getInstance(this).setLastShowADTime(System.currentTimeMillis());
        coutDownView.setCountDowningText(ResUtil.getString(R.string.splash_countdown_count)).setDuraionn(3000)
                .setCallback(new SplashCountDownView.CountDownCallbackImpl(){
                    @Override
                    public void onFinish(long delay) {
                        toMain();
                    }

                    @Override
                    public void onClicked() {
                        toMain();
                    }
                }).start();


    }



    private void toMain(){
        AppContext.login(new UserDetail());
        MainActivity.launch(SplashActivity.this);
       // ARouter.getInstance().build("/home/MainActivity").navigation();

        SplashActivity.this.finish();
    }


}
