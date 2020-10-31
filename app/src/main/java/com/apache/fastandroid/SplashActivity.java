package com.apache.fastandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.apache.artemis_annotation.ByView;
import com.apache.artemis_annotation.DIActivity;
import com.apache.fastandroid.bean.UserBean;
import com.apache.fastandroid.topic.support.config.ADConfigManager;
import com.apache.fastandroid.util.MainLogUtil;
import com.apache.fastandroid.widget.SplashCountDownView;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by jerryliu on 2017/4/10.
 */
@DIActivity
public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();

    private SplashCountDownView coutDownView;



    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(MainActivity.class.getName(), "接收到信息 ->" + msg.what);
        }
    };


    void doJob() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                mHandler.sendEmptyMessageDelayed(1, 60000L);
            }
        };
        thread.start();
    }



    @ByView(R.id.splash_app_name)
    TextView splash_app_name;

    @Override
    public int inflateContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);
        MainLogUtil.d("SplashActivity layoutInit");

        //coutDownView.setVisibility(View.VISIBLE);
        ADConfigManager.getInstance(getApplicationContext()).setLastShowADTime(System.currentTimeMillis());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMain();

            }
        },2000);
        doJob();
    }




    private void toMain(){
        //AppContext.login(new UserInfoBean());
        UserBean userBean = new UserBean();
        MainActivity.launch(SplashActivity.this,userBean);
        SplashActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            UserBean userBean = data.getParcelableExtra("userBean");
            MainLogUtil.d("userBean = %s",userBean);
        }
    }
}
