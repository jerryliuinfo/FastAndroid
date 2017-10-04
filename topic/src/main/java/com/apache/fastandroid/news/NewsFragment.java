package com.apache.fastandroid.news;

import android.os.Bundle;
import android.text.TextUtils;

import com.apache.fastandroid.news.bean.NewsBean;
import com.apache.fastandroid.news.bean.NewsBeans;
import com.apache.fastandroid.news.sdk.NewsSDK;
import com.apache.fastandroid.news.view.NewsItemViewCreator;
import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class NewsFragment extends ARecycleViewSwipeRefreshFragment<NewsBean,TopicBeans,TopicBean> {

    public static NewsFragment newFragment() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected IItemViewCreator configItemViewCreator() {
        return new NewsItemViewCreator(getActivity());
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadNewsTask(mode).execute();
    }

    class LoadNewsTask extends APagingTask<Void,Void,NewsBeans>{

        public LoadNewsTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<NewsBean> parseResult(NewsBeans newsBeans) {
            return newsBeans.list;
        }


        @Override
        protected NewsBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params)
                throws TaskException {
            int offset = 0;
            if (!TextUtils.isEmpty(nextPage)){
                offset = Integer.parseInt(nextPage);
            }
            return NewsSDK.newInstance().getNewsList(null,offset,20);
        }
    }

}
