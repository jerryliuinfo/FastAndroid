package com.apache.fastandroid.novel.find.detail;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.novel.find.bean.RecommandBeans;
import com.apache.fastandroid.novel.find.bean.RecommendBook;
import com.apache.fastandroid.novel.find.rank.view.detail.RecommandItemViewCreator;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.RefreshConfig;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/10/18.
 */

public class RecommandBooksFragment extends ARecycleViewFragment<RecommendBook,RecommandBeans,RecommendBook> {

    public static RecommandBooksFragment newFragment(String bookId) {
        Bundle args = new Bundle();
        args.putString("bookId",bookId);
        RecommandBooksFragment fragment = new RecommandBooksFragment();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("bookId",mBookId);
    }

    @Override
    protected IItemViewCreator<RecommendBook> configItemViewCreator() {
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
        protected List<RecommendBook> parseResult(RecommandBeans recommandBeans) {

            return recommandBeans.booklists;
        }

        @Override
        protected RecommandBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {

            return NovelSdk.newInstance().getRecommandBookList(mBookId, "3");
        }
    }
}
