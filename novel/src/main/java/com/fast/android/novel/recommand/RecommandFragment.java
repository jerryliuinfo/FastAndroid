package com.fast.android.novel.recommand;

import android.os.Bundle;

import com.fast.android.novel.recommand.bean.RecommendBeans;
import com.fast.android.novel.recommand.view.RecommandViewCreator;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class RecommandFragment extends ARecycleViewFragment<RecommendBeans.RecommendBook,RecommendBeans,RecommendBeans.RecommendBook> {


    public static RecommandFragment newFragment() {
        RecommandFragment fragment = new RecommandFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IItemViewCreator<RecommendBeans.RecommendBook> configItemViewCreator() {
        return new RecommandViewCreator(getActivity());
    }
}
