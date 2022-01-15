package com.apache.fastandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.apache.artemis_annotation.DIActivity;
import com.apache.fastandroid.bean.UserBean;
import com.orhanobut.logger.Logger;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by jerryliu on 2017/4/10.
 */
@DIActivity
public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    public int inflateContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);
        Logger.d("SplashActivity layoutInit");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMain();

            }
        },2000);

    }




    private void toMain(){
        UserBean userBean = new UserBean();
        MainActivity.launch(SplashActivity.this,userBean);
        SplashActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            UserBean userBean = data.getParcelableExtra("userBean");
            Logger.d("userBean = %s",userBean);
        }
    }
}
