package com.apache.fastandroid.artemis;

import android.os.Bundle;
import android.view.View;
import caom.apache.fastandroid.artemis.R;
import com.apache.fastandroid.artemis.support.bean.UserInfoBean;
import com.apache.fastandroid.artemis.util.BaseLibLogUtil;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Override
    public int inflateContentView() {
        return R.layout.user_fragment_login;
    }

    @Override
    protected void layoutInit(Bundle savedInstanceState) {
        super.layoutInit(savedInstanceState);


        findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        BaseLibLogUtil.d("onClick view id = %s, resource name = %s", v.getId(), ResUtil.getResourceName(v.getId()));
        if (v.getId() == R.id.btn_confirm){
            UserInfoBean userInfoBean = new UserInfoBean();
            AppContext.login(userInfoBean);
            LoginActivity.this.finish();
        }
    }
}
