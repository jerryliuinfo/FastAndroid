package com.fast.android.novel.find;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.fast.android.novel.R;
import com.fast.android.novel.find.bean.FindBean;
import com.fast.android.novel.find.bean.FindBeans;
import com.fast.android.novel.find.view.CommunityItemViewCreator;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class FindFragment extends ARecycleViewFragment<FindBean,FindBeans,FindBean> {

    private String type;
    public static FindFragment newFragment(String type) {
        FindFragment fragment = new FindFragment();
        Bundle args = new Bundle();
        args.putString("type",type);
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

        if (savedInstanceSate == null){
            type = getArguments().getString("type");
        }else {
            type = savedInstanceSate.getString("type");
        }

        ArrayList<FindBean> items = new ArrayList<>();
        items.add(new FindBean(ResUtil.getString(R.string.novel_general_discussion_area),R.drawable.novel_general_discussion));
        items.add(new FindBean(ResUtil.getString(R.string.novel_book_evaluate_area),R.drawable.novel_book_evaluate_area));
        items.add(new FindBean(ResUtil.getString(R.string.novel_book_shortage_area),R.drawable.novel_book_shortage_area));
        items.add(new FindBean(ResUtil.getString(R.string.novel_girl_area),R.drawable.novel_girl_area));
        items.add(new FindBean(ResUtil.getString(R.string.novel_original_area),R.drawable.novel_original_area));

        setItems(items);
    }





    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("type",type);
    }
}
