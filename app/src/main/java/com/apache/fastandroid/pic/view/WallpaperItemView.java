package com.apache.fastandroid.pic.view;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.bean.WallpaperBean;
import com.apache.fastandroid.support.utils.MainLog;
import com.tesla.framework.common.util.ActivityHelper;
import com.tesla.framework.common.util.dimen.ScreenUtil;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.fragment.itemview.ARecycleViewItemViewHolder;

/**
 * Created by 01370340 on 2017/11/19.
 */

public class WallpaperItemView extends ARecycleViewItemViewHolder<WallpaperBean>{
    public static final int LAY_RES = R.layout.item_wallpaper;

    @ViewInject(idStr = "img")
    ImageView img;

    int width;
    public WallpaperItemView(Activity context, View itemView) {
        super(context, itemView);
        width = ActivityHelper.getIntShareData(getContext(),"WallPaperWidth",0);
    }

    @Override
    public void onBindData(final View convertView, final WallpaperBean data, final int position) {
        if (width == 0){
            img.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    img.getViewTreeObserver().removeOnPreDrawListener(this);
                    width = img.getWidth();
                    NLog.d(MainLog.getLogTag(), "screen width = %s, img width = %s", ScreenUtil.getScreenWidth(),width);
                    ActivityHelper.putIntShareData(getContext(),"WallPaperWidth",width);
                    onBindData(convertView,data,position);

                    return true;
                }
            });
        }else {
            int imageW = 2160;
            int imageH = 1920;
            int height = width * imageH / imageW;
            img.setLayoutParams(new CardView.LayoutParams(width,height));
            ImageLoaderManager.getInstance().showImage(img,data.getIndexThumbnailUrl(),getContext());
        }

    }
}
