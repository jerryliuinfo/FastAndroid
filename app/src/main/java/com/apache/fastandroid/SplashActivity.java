package com.apache.fastandroid;

import android.os.Bundle;
import android.view.View;

import com.apache.fastandroid.app.AppContext;
import com.apache.fastandroid.artemis.comBridge.ActionCallback;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.support.config.ADConfigManager;
import com.apache.fastandroid.widget.SplashCountDownView;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.common.util.log.NLog;
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
        if (ADConfigManager.getInstance().isNeedToShowAd()){
            coutDownView.setVisibility(View.VISIBLE);
            ADConfigManager.getInstance().setLastShowADTime(System.currentTimeMillis());
            coutDownView.setCountDowningText(ResUtil.getString(R.string.splash_countdown_count)).setDuraionn(5000)
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
        try {
            ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:userCenter:startLoginActivity",null,null,new Object[]{SplashActivity.this});
        } catch (Exception e) {
            e.printStackTrace();
        }
        SplashActivity.this.finish();
    }


    private void toMain(){
        MainActivity.launch(SplashActivity.this);
        SplashActivity.this.finish();
    }

    /**
     * 执行登录，如果不能自动登录， 则执行onFailed,如果能
     */
    private void jump(){
        final ActionCallback callback = new ActionCallback(){

            @Override
            public void onActionSuccess(final Object... result) {
                NLog.d(TAG, "onActionSuccess (Token) result[0] = %s",(Token) result[0]);
                toMain();
                AppContext.login((Token) result[0]);

            }

            @Override
            public void onActionFailed(int code, final String msg) {
                NLog.d(TAG, "onActionFailed code = %s, msg = %s",code,msg);

                showMessage(msg);
                toLogin();
            }
        };
        try {
            ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:userCenter:doLogin",null,callback,new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
