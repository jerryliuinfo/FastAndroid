package com.apache.fastandroid.support.imageloader.fresco;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.tesla.framework.component.imageloader.IImageLoaderstrategy;
import com.tesla.framework.component.imageloader.ImageLoaderOptions;

/**
 * Created by 01370340 on 2017/11/17.
 */

public class FrescoImageLoader implements IImageLoaderstrategy {
    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {

        RequestCreator requestCreator = Picasso.with(options.getContext()).load(options.getUrl());

        if (options.getErrorDrawable() > 0){
            requestCreator.error(options.getErrorDrawable());
        }

        if (options.getHolderDrawable() > 0){
            requestCreator.placeholder(options.getHolderDrawable());
        }

        if (options.getImageSize() != null){
            requestCreator.resize(options.getImageSize().getWidth(),options.getImageSize().getHeight());
        }

        requestCreator.into((ImageView) options.getViewContainer());


    }

    @Override
    public void cleanMemory(Context context) {

    }
}
