package com.apache.fastandroid.artemis.base;

import android.os.Bundle;

import com.tesla.framework.ui.activity.BaseActivity;

/**
 * Created by 01370340 on 2017/9/2.
 */

public class MyBaseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /*//监听activity内存泄漏
        try {
            ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_MAIN_NAME+":watchLeakCancary",null,null,new Object[]{MyBaseActivity.this});
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
