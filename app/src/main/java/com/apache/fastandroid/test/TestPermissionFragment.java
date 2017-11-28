package com.apache.fastandroid.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.permission.SdcardPermissionAction;
import com.tesla.framework.support.action.IAction;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by 01370340 on 2017/11/22.
 */

public class TestPermissionFragment extends ABaseFragment {

    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,TestPermissionFragment.class,args);
    }


    @Override
    public int inflateContentView() {
        return R.layout.act_dialog_test;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        setToolbarTitle("测试权限");
        findViewById(R.id.btn_require_sdcard_permission).setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (getActivity() instanceof BaseActivity){
                    final BaseActivity baseActivity = (BaseActivity) getActivity();
                    new IAction(getActivity(), new SdcardPermissionAction(baseActivity,null)){
                        @Override
                        public void doAction() {
                            super.doAction();
                            baseActivity.showMessage("申请权限成功");
                        }
                    }.run();
                }

            }
        });
    }





}
