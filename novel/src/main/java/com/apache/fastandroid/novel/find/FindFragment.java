package com.apache.fastandroid.novel.find;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.FindBean;
import com.apache.fastandroid.novel.find.bean.FindBeans;
import com.apache.fastandroid.novel.find.view.CommunityItemViewCreator;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class FindFragment extends ARecycleViewFragment<FindBean,FindBeans,FindBean> {

    private String mType;

    public static FindFragment newFragment(String type) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString("mType",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IItemViewCreator<FindBean> configItemViewCreator() {
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
        ArrayList<FindBean> items = null;
        if (savedInstanceSate == null){
            mType = getArguments().getString("mType");
        }else {
            mType = savedInstanceSate.getString("mType");
        }
        setItems(generateItems());
    }



    private ArrayList<FindBean> generateItems(){
        ArrayList<FindBean> items = new ArrayList<>();
        //社区
        if ("1".equals(mType)){
            items.add(new FindBean(ResUtil.getString(R.string.novel_general_discussion_area),R.drawable.novel_general_discussion));
            items.add(new FindBean(ResUtil.getString(R.string.novel_book_evaluate_area),R.drawable.novel_book_evaluate_area));
            items.add(new FindBean(ResUtil.getString(R.string.novel_book_shortage_area),R.drawable.novel_book_shortage_area));
            items.add(new FindBean(ResUtil.getString(R.string.novel_girl_area),R.drawable.novel_girl_area));
            items.add(new FindBean(ResUtil.getString(R.string.novel_original_area),R.drawable.novel_original_area));
        }
        //发现
        else if ("2".equals(mType)){
            //排行榜
            items.add(new FindBean(ResUtil.getString(R.string.novel_rank),R.drawable.home_find_rank));
            //主题书单
            items.add(new FindBean(ResUtil.getString(R.string.novel_topic),R.drawable.home_find_topic));
            //分类
            items.add(new FindBean(ResUtil.getString(R.string.novel_category),R.drawable.home_find_category));
            //官方QQ群
            items.add(new FindBean(ResUtil.getString(R.string.novel_offical_qq),R.drawable.home_find_listen));
        }
        return items;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mType", mType);
    }
}
