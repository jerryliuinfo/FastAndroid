package com.apache.fastandroid.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.R;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by jerryliu on 2017/6/24.
 */

public class SettingFragment extends ABaseFragment {
    public static void launch(Activity from){
        FragmentContainerActivity.launch(from,SettingFragment.class,null );
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        BaseActivity activity = (BaseActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        activity.getSupportActionBar().setTitle("设置");
    }

    @Override
    public int inflateContentView() {
        return R.layout.fragment_setting;
    }
}
