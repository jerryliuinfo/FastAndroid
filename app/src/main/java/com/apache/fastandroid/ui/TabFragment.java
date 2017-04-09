package com.apache.fastandroid.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.tesla.framework.support.bean.TabItem;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by jerryliu on 2017/4/9.
 */

public class TabFragment extends ABaseFragment {
    public static TabFragment newFragment(TabItem tabItem) {
        Bundle args = new Bundle();
        args.putString("title", tabItem.getTitle());
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewInject(idStr = "tv_name")
    TextView tv_name;


    @Override
    public int inflateContentView() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        String title = "";
        if (getArguments() != null){
            title = (String) getArguments().get("title");
        }
        tv_name.setText(title);
    }
}
