package com.apache.fastandroid.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.R;
import com.apache.fastandroid.news.MainNewsTabsFragment;
import com.apache.fastandroid.novel.find.rank.TopRankListFragment;
import com.apache.fastandroid.topic.TopicListFragment;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by 01370340 on 2017/11/27.
 */

public class TestFragment extends ABaseFragment {

    public static TestFragment newFragment() {
         Bundle args = new Bundle();
         TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,TestFragment.class,args);
    }

    @Override
    public int inflateContentView() {
        return R.layout.test_fragment;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        setToolbarTitle("test");
        findViewById(R.id.btn_test_worktask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestWorkTaskFragment.launch(getActivity());
            }
        });
        findViewById(R.id.btn_test_swipe_refresh_recycleview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopicListFragment.launch(getActivity());
            }
        });
        findViewById(R.id.btn_test_recycleView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TopRankListFragment.launch(getActivity());
            }
        });
        findViewById(R.id.btn_header_recycleView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRecycleHeaderViewFragment.launch(getActivity());
            }
        });
        findViewById(R.id.btn_multi_type_recycleView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNewsTabsFragment.launch(getActivity());
            }
        });
        findViewById(R.id.btn_test_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestPermissionFragment.launch(getActivity());
            }
        });

    }
}
