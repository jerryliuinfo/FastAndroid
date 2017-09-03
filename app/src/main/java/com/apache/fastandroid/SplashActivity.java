package com.apache.fastandroid;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.apache.fastandroid.app.AppContext;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.support.bean.UserBean;
import com.apache.fastandroid.support.config.ADConfigManager;
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
        if (ADConfigManager.getInstance().isNeedToShowAd()){
            coutDownView.setVisibility(View.VISIBLE);
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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    jump();
                }
            },2000);
        }
    }

    private void toLogin(){
        //LoginFragment.start(this);
        Object[] extras = new Object[]{SplashActivity.this};
        try {
            ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:userCenter:startLoginActivity",null,null,extras);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SplashActivity.this.finish();
    }

    private void toMain(){
        MainActivity.launch(SplashActivity.this);
        SplashActivity.this.finish();
    }

    private void jump(){
        IActionDelegate.IActionCallback callback = new IActionDelegate.IActionCallback() {
            @Override
            public void onActionPrepare() {

            }

            @Override
            public void onActionSuccess(Object... result) {
                toMain();
                AppContext.login((UserBean) result[0]);
            }

            @Override
            public void onActionFailed(int code, String msg) {
                toLogin();
            }

            @Override
            public void onActionFinish() {

            }
        };
        try {
            ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:userCenter:autoLogin", null, callback, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* new WorkTask<Void,Void,UserBean>(){

            @Override
            public UserBean workInBackground(Void... params) throws TaskException {
                return UserSDK.newInstance().doAutoLogin();
            }

            @Override
            protected void onSuccess(UserBean userBean) {
                super.onSuccess(userBean);
                if (userBean != null){
                    toMain();
                }else {
                    toLogin();
                }
            }

            *//*@Override
            protected void onSuccess(UserBean aBoolean) {
                super.onSuccess(aBoolean);
                if (aBoolean){
                    toMain();

                }else {
                    toLogin();
                }
            }*//*

            @Override
            protected void onFailure(TaskException exception) {
                super.onFailure(exception);
                toLogin();
            }

        }.execute();*/

    }


}
