package com.apache.fastandroid.novel.find.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.RecommandBeans;
import com.apache.fastandroid.novel.find.detail.view.RecommandItemViewCreator;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/10/18.
 */

public class RecommandBooksFragment extends ARecycleViewFragment<RecommandBeans.RecommendBean,RecommandBeans,RecommandBeans.RecommendBean> {

    public static RecommandBooksFragment newFragment(String bookId) {
        Bundle args = new Bundle();
        args.putString("bookId",bookId);
        RecommandBooksFragment fragment = new RecommandBooksFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewInject(idStr = "lay_recommand")
    View lay_recommand;

    @Override
    public int inflateContentView() {
        return R.layout.novel_book_detail_recommand_list;
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bookId",mBookId);
    }

    @Override
    public IItemViewCreator<RecommandBeans.RecommendBean> configItemViewCreator() {
        return new RecommandItemViewCreator(getActivity());
    }


    @Override
    protected void setUpRefreshConfig(RefreshConfig refreshConfig) {
        super.setUpRefreshConfig(refreshConfig);
        refreshConfig.disalbeFooterMore();
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);

        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadCommentTask(mode).execute();

    }

    class LoadCommentTask extends APagingTask<Void,Void,RecommandBeans>{

        public LoadCommentTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<RecommandBeans.RecommendBean> parseResult(RecommandBeans recommandBeans) {

            return recommandBeans.booklists;
        }

        @Override
        protected RecommandBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {

            return NovelSdk.getInstance().getRecommandBookList(mBookId, "3");
        }

        @Override
        protected void onSuccess(RecommandBeans recommandBeans) {
            super.onSuccess(recommandBeans);
            lay_recommand.setVisibility(View.VISIBLE);
        }
    }
}
