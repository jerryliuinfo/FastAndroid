package com.apache.fastandroid.test;

import android.app.Activity;
import android.text.TextUtils;

import com.apache.fastandroid.news.bean.NewsBean;
import com.apache.fastandroid.news.bean.NewsBeans;
import com.apache.fastandroid.news.sdk.NewsSDK;
import com.apache.fastandroid.test.view.TestItemViewCreator;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.CustomIndexPaging;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by 01370340 on 2017/11/30.
 */

public class TestSwipeRefreshLayoutRecycleViewFragment extends ARecycleViewSwipeRefreshFragment<NewsBean,NewsBeans,NewsBean> {
   public static void launch(Activity from) {
       FragmentArgs args =  new FragmentArgs();
       FragmentContainerActivity.launch(from,TestSwipeRefreshLayoutRecycleViewFragment.class,args);
   }

    @Override
    public IItemViewCreator<NewsBean> configItemViewCreator() {
        return new TestItemViewCreator(getActivity());
    }

    protected int pageSize = 20;                     // 每页加载条数

    @Override
    protected IPaging<NewsBean, NewsBeans> newPaging() {
        CustomIndexPaging paging = new CustomIndexPaging();
        paging.setPageSize(pageSize);
        return paging;
    }


    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        //对于index,offset 分页都是没有下拉的，重置为reset模式
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
