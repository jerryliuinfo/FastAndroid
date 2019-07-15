package com.apache.fastandroid.artemis;

import android.os.Bundle;
import android.view.View;
import caom.apache.fastandroid.artemis.R;
import com.apache.fastandroid.artemis.support.bean.UserInfoBean;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
public class LoginActivity extends BaseActivity {
    @Override
    public int inflateContentView() {
        return R.layout.user_fragment_login;
    }

    @Override
    protected void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);


        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoBean userInfoBean = new UserInfoBean();
                AppContext.login(userInfoBean);
                LoginActivity.this.finish();

            }
        });

    }
}
