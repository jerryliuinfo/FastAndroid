package com.apache.fastandroid.support.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.apache.fastandroid.support.utils.MainLog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.imageloader.IImageLoaderInit;
import com.tesla.framework.component.imageloader.IImageLoaderstrategy;
import com.tesla.framework.component.imageloader.ImageLoaderOptions;

/**
 * Created by 01370340 on 2017/11/17.
 */

public class PicasoImageLoader implements IImageLoaderstrategy,IImageLoaderInit {
    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {

        RequestCreator requestCreator = Picasso.with(options.getContext()).load(options.getUrl());

        if (options.getHolderId() > 0){
            requestCreator.placeholder(options.getHolderId());
        }

        if (options.getHolderDrawable() != null){
            requestCreator.placeholder(options.getHolderDrawable());
        }

        if (options.getErrorId() > 0){
            requestCreator.error(options.getErrorId());
        }
        if (options.getErrorDrawable() != null){
            requestCreator.error(options.getErrorDrawable());
        }




        if (options.getImageSize() != null){
            requestCreator.resize(options.getImageSize().getWidth(),options.getImageSize().getHeight());
        }
        requestCreator.into((ImageView) options.getViewContainer());
    }

    @Override
    public void cleanMemory(Context context) {

    }


    @Override
    public void init(Context context) {
        NLog.d(MainLog.getLogTag(), "PicasoImageLoader init");

    }
}
