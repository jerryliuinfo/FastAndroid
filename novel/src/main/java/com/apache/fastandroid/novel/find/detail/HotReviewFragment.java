package com.apache.fastandroid.novel.find.detail;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.novel.find.bean.HotReviewBean;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.apache.fastandroid.novel.support.util.NovelLog;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/10/21.
 */

public class HotReviewFragment extends ARecycleViewFragment<HotReviewBean.Reviews,HotReviewBean,HotReviewBean.Reviews> {

    public static HotReviewFragment newFragment(String bookId) {
        Bundle args = new Bundle();
        args.putString("bookId",bookId);
        HotReviewFragment fragment = new HotReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String mBookId;

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        if (savedInstanceSate == null){
            mBookId = getArguments().getString("bookId");
        }else {
            mBookId = savedInstanceSate.getString("bookId");
        }
    }

    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        refreshConfig.disalbeFooterMore();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bookId",mBookId);
    }

    @Override
    protected IItemViewCreator<HotReviewBean.Reviews> configItemViewCreator() {
        return new HotReviewItemViewCreator(getActivity());
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }

        new LoadTask(mode).execute();
    }


    class LoadTask extends APagingTask<Void,Void,HotReviewBean>{

        public LoadTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<HotReviewBean.Reviews> parseResult(HotReviewBean hotReviewBean) {
            return hotReviewBean.reviews;
        }

        @Override
        protected HotReviewBean workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            HotReviewBean bean =  NovelSdk.newInstance().getHotReview(mBookId);
            NovelLog.d("getHotReview bean = %s", bean);
            return bean;
        }
    }

}
