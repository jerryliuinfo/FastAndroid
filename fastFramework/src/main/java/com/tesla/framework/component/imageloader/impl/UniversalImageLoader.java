package com.tesla.framework.component.imageloader.impl;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tesla.framework.component.imageloader.IImageLoaderstrategy;
import com.tesla.framework.component.imageloader.ImageLoaderOptions;

/**
 * Created by 01370340 on 2017/11/18.
 */

public class UniversalImageLoader implements IImageLoaderstrategy {
    @Override
    public void showImage(@NonNull final ImageLoaderOptions options) {
        ImageSize imageSize = null;
        if (options.getImageSize() != null){
            imageSize = new ImageSize(options.getImageSize().getWidth(),options.getImageSize().getHeight());
        }
        ImageLoadingListener listener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                ImageView imageView = (ImageView) options.getViewContainer();
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        };

        ImageLoader.getInstance().loadImage(options.getUrl(),imageSize,apply(options),listener);

    }

    @Override
    public void cleanMemory(Context context) {

    }

    @Override
    public void init(Context context) {

    }

    @Override
    public void pause(Context context) {

    }

    @Override
    public void resume(Context context) {

    }

    /*@Override
    public void init(Context context) {
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(context);
        ImageLoader.getInstance().init(configuration);
    }*/

    private DisplayImageOptions apply(ImageLoaderOptions options){
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();

        if (options.getHolderId() > 0){
            builder.showImageOnLoading(options.getHolderId());
        }

        if (options.getHolderDrawable() != null){
            builder.showImageOnLoading(options.getHolderDrawable());
        }

        if (options.getErrorId() > 0){
            builder.showImageOnFail(options.getErrorId());
        }

        if (options.getErrorDrawable() != null){
            builder.showImageOnFail(options.getErrorDrawable());
        }

        if (TextUtils.isEmpty(options.getUrl())){
            if (options.getEmptyUrlId() > 0){
                builder.showImageForEmptyUri(options.getEmptyUrlId());
            }
            if (options.getEmptyUrlDrawable() != null){
                builder.showImageForEmptyUri(options.getEmptyUrlDrawable());
            }
        }
        builder.cacheInMemory(options.isSkipMemoryCache());

        if (options.getDiskCacheStrategy() != ImageLoaderOptions.DiskCacheStrategy.DEFAULT) {
            switch (options.getDiskCacheStrategy()) {
                case NONE:
                    builder.cacheOnDisk(false);
                    break;
                case All:
                case SOURCE:
                case RESULT:
                   builder.cacheOnDisk(true);
                    break;
                default:
                    break;
            }
        }



        return builder.build();

    }



}
