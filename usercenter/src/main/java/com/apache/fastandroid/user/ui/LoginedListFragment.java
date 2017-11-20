package com.apache.fastandroid.user.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.usercenter.R;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by 01370340 on 2017/11/18.
 */

public class LoginedListFragment extends ABaseFragment {
    public static void start(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,LoginedListFragment.class,args);
    }

    @Override
    public int inflateContentView() {
        return R.layout.fragment_user_list;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        setToolbarTitle("账号管理");
    }
}
