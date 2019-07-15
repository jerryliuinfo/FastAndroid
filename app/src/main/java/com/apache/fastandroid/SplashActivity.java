package com.apache.fastandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
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
    //@ViewInject(id = R.id.splash_coutdown_view)


    private SplashCountDownView coutDownView;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            MainLogUtil.d("handleMessage");
            testBlock();
        }
    };

    private void testBlock(){
        SystemClock.sleep(600);
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

        //coutDownView.setVisibility(View.VISIBLE);
        ADConfigManager.getInstance(this).setLastShowADTime(System.currentTimeMillis());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toMain();


            }
        },2000);
        testBlock();

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
