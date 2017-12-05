package com.apache.fastandroid.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.app.MyApplication;
import com.apache.fastandroid.artemis.base.BaseFragment;
import com.tesla.framework.component.bridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.bridge.ModuleConstans;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.activity.FragmentContainerActivity;

/**
 * Created by jerryliu on 2017/6/24.
 */

public class SettingFragment extends BaseFragment {
    public static void launch(Activity from){
        FragmentContainerActivity.launch(from,SettingFragment.class,null );
    }

    @ViewInject(idStr = "tv_clear_cache")
    private TextView tv_clear_cache;

    @ViewInject(idStr = "tv_about")
    private TextView tv_about;

    @ViewInject(idStr = "tv_logout")
    private TextView tv_logout;

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        BaseActivity activity = (BaseActivity) getActivity();
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
        activity.getSupportActionBar().setTitle("设置");

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AppContext.logout(MyApplication.getContext());
                try {
                    ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_MAIN_NAME+":logout",null,null,new Object[]{MyApplication.getContext()});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int inflateContentView() {
        return R.layout.fragment_setting;
    }
}
