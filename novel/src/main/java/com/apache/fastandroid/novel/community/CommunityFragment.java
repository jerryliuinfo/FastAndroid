package com.apache.fastandroid.novel.community;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.FindBean;
import com.apache.fastandroid.novel.find.bean.FindBeans;
import com.apache.fastandroid.novel.find.view.CommunityItemViewCreator;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class CommunityFragment extends ARecycleViewFragment<FindBean,FindBeans,FindBean> {

    public static CommunityFragment newFragment() {
        CommunityFragment fragment = new CommunityFragment();
        return fragment;
    }
    
    public static void launch(Activity from) {
        FragmentArgs args =  new FragmentArgs();
        FragmentContainerActivity.launch(from,CommunityFragment.class,args);
    }

    @Override
    public IItemViewCreator<FindBean> configItemViewCreator() {
        return new CommunityItemViewCreator(getActivity());
    }

    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        refreshConfig.disalbeFooterMore();
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);



        ArrayList<FindBean> items = new ArrayList<>();
        items.add(new FindBean(ResUtil.getString(R.string.novel_general_discussion_area),R.drawable.novel_general_discussion));
        items.add(new FindBean(ResUtil.getString(R.string.novel_book_evaluate_area),R.drawable.novel_book_evaluate_area));
        items.add(new FindBean(ResUtil.getString(R.string.novel_book_shortage_area),R.drawable.novel_book_shortage_area));
        items.add(new FindBean(ResUtil.getString(R.string.novel_girl_area),R.drawable.novel_girl_area));
        items.add(new FindBean(ResUtil.getString(R.string.novel_original_area),R.drawable.novel_original_area));

        setItems(items);
    }






}
