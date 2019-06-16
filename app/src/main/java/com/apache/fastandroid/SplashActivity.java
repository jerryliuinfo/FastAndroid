package com.apache.fastandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.apache.fastandroid.artemis.AppContext;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.bean.UserBean;
import com.apache.fastandroid.topic.support.config.ADConfigManager;
import com.apache.fastandroid.widget.SplashCountDownView;
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
    public int inflateContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);

        //coutDownView.setVisibility(View.VISIBLE);
        ADConfigManager.getInstance(this).setLastShowADTime(System.currentTimeMillis());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMain();
            }
        },3000);
    }




    private void toMain(){
        AppContext.login(new UserDetail());
        UserBean userBean = new UserBean();
        MainActivity.launch(SplashActivity.this,userBean);
        SplashActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            UserBean userBean = data.getParcelableExtra("userBean");
            NLog.d("userBean = %s",userBean);
        }
    }
}
