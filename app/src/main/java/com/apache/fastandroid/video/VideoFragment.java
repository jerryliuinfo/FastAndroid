package com.apache.fastandroid.video;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.apache.fastandroid.video.bean.VideoBean;
import com.apache.fastandroid.video.bean.VideoResultBean;
import com.apache.fastandroid.video.view.VideoItemViewCreator;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class VideoFragment extends ARecycleViewFragment<VideoBean,VideoResultBean,VideoBean> {

    public static final String EXTRA_CATEGORY = "category";
    private String mCategory;
    public static VideoFragment newFragment(String category) {
         Bundle args = new Bundle();
         args.putString(EXTRA_CATEGORY, category);
         VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);
        if (savedInstanceSate == null){
            mCategory = getArguments().getString(EXTRA_CATEGORY);
        }else {
            mCategory = savedInstanceSate.getString(EXTRA_CATEGORY);
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_CATEGORY, mCategory);
    }

    @Override
    protected IPaging<VideoBean, VideoResultBean> newPaging() {
        return new IndexPaging<>();
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        NLog.d(TAG, "requestData mCategory = %s", mCategory);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadVideoTask(mode).execute();
    }

    @Override
    public IItemViewCreator<VideoBean> configItemViewCreator() {
        return new VideoItemViewCreator(getActivity());
    }


    class LoadVideoTask extends APagingTask<Void,Void,VideoResultBean>{

        public LoadVideoTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<VideoBean> parseResult(VideoResultBean videoResultBean) {
            return videoResultBean.videos;
        }

        @Override
        protected VideoResultBean workInBackground(RefreshMode mode, String previousPage, String nextPage, Void...
                params) throws TaskException {
            int page = Integer.parseInt(nextPage);
            VideoResultBean result = VideoSDK.newInstance(getTaskCacheMode(this)).loadViedoData(mCategory,page);
            NLog.d(TAG, "result = %s", result);
            return result;
        }


    }



}
