package com.apache.fastandroid.ui.fragment.video;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.bean.VideoBean;
import com.apache.fastandroid.support.bean.VideoResultBean;
import com.apache.fastandroid.support.sdk.VideoSDK;
import com.bumptech.glide.Glide;
import com.tesla.framework.common.util.Logger;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.fragment.ARecycleViewFragment;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;
import com.tesla.framework.ui.fragment.itemview.IITemView;
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
        Logger.d(TAG, "requestData mCategory = %s", mCategory);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadVideoTask(mode).execute();
    }

    @Override
    protected IItemViewCreator<VideoBean> configItemViewCreator() {
        return new IItemViewCreator<VideoBean>() {
            @Override
            public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
                return inflater.inflate(ImageItemView.LAY_RES,parent,false);
            }

            @Override
            public IITemView<VideoBean> newItemView(View contentView, int viewType) {
                return new ImageItemView(getActivity(),contentView);
            }
        };
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
            Logger.d(TAG, "result = %s", result);
            return result;
        }


    }


    static class ImageItemView extends ARecycleViewItemViewHolder<VideoBean> {
        public static final int LAY_RES = R.layout.item_video;
        @ViewInject(id = R.id.iv_cover)
        ImageView iv_cover;
        @ViewInject(id = R.id.tv_desc)
        TextView tv_desc;

        @ViewInject(id = R.id.ib_play)
        ImageButton ib_play;

        public ImageItemView(Activity context, View itemView) {
            super(context, itemView);
        }

        @Override
        public void onBindData(View convertView, VideoBean data, int position) {
            Glide.with(getContext()).load(data.thumbnail_v2).into(iv_cover);
            tv_desc.setText(data.title);
            ib_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

}
