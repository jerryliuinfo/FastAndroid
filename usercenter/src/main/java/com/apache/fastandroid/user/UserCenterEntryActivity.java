package com.apache.fastandroid.user;

import android.os.Bundle;

import com.apache.fastandroid.user.ui.LoginFragmentMvp;
import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by 01370340 on 2017/10/30.
 */

public class UserCenterEntryActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LoginFragmentMvp.start(this,false);
        finish();
    }
}
