package com.apache.fastandroid;

import android.os.Bundle;
import android.view.View;

import com.apache.fastandroid.app.AppContext;
import com.apache.fastandroid.artemis.bridge.ModuleConstans;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.support.config.ADConfigManager;
import com.apache.fastandroid.widget.SplashCountDownView;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.bridge.ActionCallback;
import com.tesla.framework.component.bridge.ModularizationDelegate;
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
        coutDownView.setCountDowningText(ResUtil.getString(R.string.splash_countdown_count)).setDuraionn(5000)
                .setCallback(new SplashCountDownView.CountDownCallbackImpl(){
                    @Override
                    public void onFinish(long delay) {
                        doAutoLogin();
                    }

                    @Override
                    public void onClicked() {
                        doAutoLogin();
                    }
                }).start();

    }

    private void toLogin(){
        try {
            ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_USER_CENTER_NAME+":startLoginActivity",null,null,new Object[]{SplashActivity.this});
        } catch (Exception e) {
            e.printStackTrace();
        }
        SplashActivity.this.finish();
    }


    private void toMain(Token token){
        if (token != null){
            showMessage("自动登录成功");
        }
        AppContext.login(token);
        MainActivity.launch(SplashActivity.this);
        SplashActivity.this.finish();
        //toLogin();
    }

    /**
     * 执行登录，如果不能自动登录， 则执行onFailed进入登录界面,
     * 否则进入主界面
     */
    private void doAutoLogin(){
        final ActionCallback callback = new ActionCallback(){

            @Override
            public void onActionPrepare() {
                super.onActionPrepare();
            }

            @Override
            public void onActionSuccess(final Object... result) {
                NLog.d(TAG, "onActionSuccess (Token) result[0] = %s",(Token) result[0]);
                toMain((Token) result[0]);


            }

            @Override
            public void onActionFailed(int code, final String msg) {
                NLog.d(TAG, "onActionFailed code = %s, msg = %s",code,msg);

                //showMessage(msg);
                //自动登录失败 跳转到登录界面 执行手动登录
                toLogin();
                //toMain(null);
            }
        };
        /*try {
            ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_USER_CENTER_NAME+":doLogin",null,callback,new Object[]{SplashActivity.this});
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        toMain(null);
    }


}
