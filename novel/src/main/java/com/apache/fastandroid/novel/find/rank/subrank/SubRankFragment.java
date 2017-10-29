package com.apache.fastandroid.novel.find.rank.subrank;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.novel.find.bean.BookBean;
import com.apache.fastandroid.novel.find.bean.BooksByCats;
import com.apache.fastandroid.novel.find.bean.Rankings;
import com.apache.fastandroid.novel.find.rank.view.SubRankItemViewCreator;
import com.apache.fastandroid.novel.support.sdk.NovelSdk;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/10/10.
 */

public class SubRankFragment extends ARecycleViewSwipeRefreshFragment<BookBean,BooksByCats,BookBean> {

    private String mRankingId;

    public static SubRankFragment newFragment(String rankingId) {
        Bundle args = new Bundle();
        args.putString("rankingId", rankingId);
        SubRankFragment fragment = new SubRankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        if (savedInstanceSate == null){
            mRankingId = getArguments().getString("rankingId");
        }else {
            mRankingId = savedInstanceSate.getString("rankingId");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("rankingId", mRankingId);
    }

    @Override
    protected IItemViewCreator<BookBean> configItemViewCreator() {
        return new SubRankItemViewCreator(getActivity());
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadDataTask(mode).execute();
    }

    class LoadDataTask extends APagingTask<Void,Void,Rankings>{


        public LoadDataTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<BookBean> parseResult(Rankings rankings) {
            if (rankings != null && rankings.ranking != null){
                return rankings.ranking.books;
            }
            return null;
        }

        @Override
        protected Rankings workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params)
                throws TaskException {
            return NovelSdk.getInstance().getRanking(mRankingId);
        }
    }
}
