package com.apache.fastandroid.ui.fragment.pic;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apache.fastandroid.MyApplication;
import com.apache.fastandroid.R;
import com.apache.fastandroid.support.bean.ImageBean;
import com.apache.fastandroid.support.bean.ImageResultBeans;
import com.apache.fastandroid.support.sdk.PicSDK;
import com.apache.fastandroid.ui.widget.PicImageView;
import com.apache.fastandroid.ui.widget.SpaceItemDecoration;
import com.bumptech.glide.Glide;
import com.tesla.framework.common.util.DimensUtil;
import com.tesla.framework.common.util.SystemUtils;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.fragment.ARecycleViewStaggeredGridFragment;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import java.util.List;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class PicFragment extends ARecycleViewStaggeredGridFragment<ImageBean,ImageResultBeans,ImageBean> {

    public static final String EXTRA_CATEGORY = "category";
    private String mCategory;
    public static PicFragment newFragment(String category) {
         Bundle args = new Bundle();
        args.putString(EXTRA_CATEGORY, category);
         PicFragment fragment = new PicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setUpRefreshView() {
        super.setUpRefreshView();
        getRefreshView().addItemDecoration(new SpaceItemDecoration(DimensUtil.dip2px(8)));
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
    protected IPaging<ImageBean, ImageResultBeans> newPaging() {
        return new IndexPaging<>();
    }

    @Override
    public void requestData(RefreshMode mode) {
        super.requestData(mode);
        NLog.d(TAG, "requestData mCategory = %s", mCategory);
        if (mode == RefreshMode.refresh){
            mode = RefreshMode.reset;
        }
        new LoadImageTask(mode).execute();
    }

    @Override
    protected IItemViewCreator<ImageBean> configItemViewCreator() {
        return new IItemViewCreator<ImageBean>() {
            @Override
            public View newContentView(LayoutInflater inflater, ViewGroup parent, int viewType) {
                return inflater.inflate(ImageItemView.LAY_RES,parent,false);
            }

            @Override
            public IITemView<ImageBean> newItemView(View contentView, int viewType) {
                return new ImageItemView(getActivity(),contentView);
            }
        };
    }


    class LoadImageTask extends APagingTask<Void,Void,ImageResultBeans>{

        public LoadImageTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<ImageBean> parseResult(ImageResultBeans imageResultBeans) {
            return imageResultBeans.imgs;
        }

        @Override
        protected ImageResultBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            int pageNum = Integer.parseInt(nextPage);
            ImageResultBeans result =  PicSDK.newInstance(getTaskCacheMode(this)).loadImageData(mCategory,pageNum);
            NLog.d(TAG, "result = %s", result);
            return result;
        }
    }


    static class ImageItemView extends ARecycleViewItemViewHolder<ImageBean>{
        public static final int LAY_RES = R.layout.item_pic;
        @ViewInject(id = R.id.iv_pic)
        PicImageView iv_pic;

        public ImageItemView(Activity context, View itemView) {
            super(context, itemView);
        }

        @Override
        public void onBindData(View convertView, ImageBean data, int position) {
            int width = data.thumbnailWidth;
            int height = data.thumbnailHeight;
            iv_pic.setImageHeight(height);
            iv_pic.setImageWidth(width);
            Glide.with(getContext()).load(data.thumbnailUrl).
                    override(SystemUtils.getScreenWidth(MyApplication.getContext()), DimensUtil.dip2px(100)).into(iv_pic);
        }
    }






}
