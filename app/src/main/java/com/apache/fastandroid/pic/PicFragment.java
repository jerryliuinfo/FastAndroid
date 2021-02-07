package com.apache.fastandroid.pic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.apache.fastandroid.R;
import com.apache.fastandroid.topic.support.bean.ImageBean;
import com.apache.fastandroid.topic.support.bean.ImageResultBeans;
import com.apache.fastandroid.topic.support.utils.FastAndroidUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.tesla.framework.common.util.dimen.DimensUtil;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.support.paging.IPaging;
import com.tesla.framework.support.paging.index.IndexPaging;
import com.tesla.framework.ui.fragment.ARecycleViewSwipeRefreshFragment;
import com.tesla.framework.ui.fragment.ATabsFragment;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;
import com.tesla.framework.ui.fragment.itemview.IITemView;
import com.tesla.framework.ui.fragment.itemview.IItemViewCreator;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class PicFragment extends ARecycleViewSwipeRefreshFragment<ImageBean,ImageResultBeans,ImageBean> implements ATabsFragment.ITabInitData{
    public static final String TAG = PicFragment.class.getSimpleName();
    public static final String EXTRA_CATEGORY = "category";
    private String mCategory;
    public static PicFragment newFragment(String category) {
        PicFragment fragment = new PicFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_CATEGORY, category);

        fragment.setArguments(args);
        return fragment;
    }
    public static final int SPAN_COUNT = 2;

    protected int getSpanCount() {
        return SPAN_COUNT;
    }

    @Override
    protected RecyclerView.LayoutManager configLayoutManager() {
        //return new GridLayoutManager(getSpanCount(),StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(getSpanCount(),StaggeredGridLayoutManager.VERTICAL);

        return layoutManager;
    }

    @Override
    protected void setUpRefreshView() {
        super.setUpRefreshView();
        int padding = DimensUtil.dp2px(4);
        getRefreshView().setPadding(padding, 0, padding, 0);
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
        boolean load = true;
        //通过这个checkTabsFragmentCanRequestData方法实现懒加载
        if (getTaskCount(PAGING_TASK_ID) == 0){
            load = FastAndroidUtils.checkTabsFragmentCanRequestData(this);
        }
        FastLog.d(TAG, "requestData mCategory = %s, task count = %s,load = %s", mCategory,getTaskCount(PAGING_TASK_ID),load);
        if (load){
            if (mode == RefreshMode.refresh){
                mode = RefreshMode.reset;
            }
            new LoadImageTask(mode).execute();
        }
    }


    @Override
    public IItemViewCreator<ImageBean> configItemViewCreator() {
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

    @Override
    public void onTabRequestData() {
        FastLog.d(TAG, "onTabRequestData task count = %s", getTaskCount(PAGING_TASK_ID));
        // 如果还没有加载过数据，就开始加载,否则不加载, 这里必须要做taskCount为0的判断，否则每次切换tab的时候都会
        //重新拉取数据
        if (getTaskCount(PAGING_TASK_ID) == 0) {
            requestData(RefreshMode.reset);
        }
    }


    class LoadImageTask extends APagingTask<Void,Void,ImageResultBeans>{

        public LoadImageTask(RefreshMode mode) {
            super(mode);
        }

        @Override
        protected List<ImageBean> parseResult(ImageResultBeans imageResultBeans) {
            final List<ImageBean> tempImages = new ArrayList<>();
            if (imageResultBeans.imgs != null && imageResultBeans.imgs.size() > 0){
                for (ImageBean imageBean: imageResultBeans.imgs){
                    if (imageBean.imageWidth > 0 && imageBean.imageHeight > 0){
                        tempImages.add(imageBean);
                    }
                }
            }
            return tempImages;
        }

        @Override
        protected ImageResultBeans workInBackground(RefreshMode mode, String previousPage, String nextPage, Void... params) throws TaskException {
            int pageNum = 1;
            if (!TextUtils.isEmpty(nextPage)){
                pageNum = Integer.parseInt(nextPage);
            }
            ImageResultBeans result = null;
            // result =  PicSDK.getInstance(getTaskCacheMode(this)).loadImageData(mCategory,pageNum);

            Call<ImageResultBeans> call = PicSDK.newInstance(getTaskCacheMode(this)).loadImageDataV2(mCategory,pageNum);

            Response<ImageResultBeans> response = null;
            try {
                response = call.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (response != null ){
                result = response.body();
            }

//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return result;
        }
    }


    static class ImageItemView extends ARecycleViewItemViewHolder<ImageBean>{
        public static final int LAY_RES = R.layout.item_pic;
        @ViewInject(id = R.id.iv_pic)
        ImageView iv_pic;

//        @ViewInject(id = R.id.pic_img)
//        PicImageView pic_img;

        int width;

        public ImageItemView(Activity context, View itemView) {
            super(context, itemView);
            width = (ScreenUtils.getScreenWidth() - DimensUtil.dp2px(8))  / SPAN_COUNT ;
        }

        @Override
        public void onBindData(View convertView, ImageBean data, int position) {
            int imageWidth = data.thumbnailWidth;
            int imageHeight = data.thumbnailHeight;

            int height = width * imageHeight / imageWidth;
            iv_pic.setLayoutParams(new RelativeLayout.LayoutParams(width,height));

            if (!TextUtils.isEmpty(data.thumbnailUrl)){
                ImageLoaderManager.getInstance().showImage(iv_pic,data.thumbnailUrl,getContext());
            }

        }
    }

    @Override
    public boolean OnToolbarDoubleClick() {
        if (FastAndroidUtils.checkTabsFragmentCanRequestData(this)){
            requestDataDelaySetRefreshing(500);
            getRefreshView().scrollToPosition(0);
            return true;
        }
        return false;
    }


    /*@Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return isFooter(getItemViewType(position)) ? gridLayoutManager.getSpanCount(): 1;
                }
            });
        }
    }*/



}
