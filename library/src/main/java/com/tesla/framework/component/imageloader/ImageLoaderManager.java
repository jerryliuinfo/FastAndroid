package com.tesla.framework.component.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;


/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class ImageLoaderManager implements IImageLoaderstrategy {
    private static final ImageLoaderManager INSTANCE=new ImageLoaderManager();
    private  IImageLoaderstrategy loaderstrategy;
    private ImageLoaderManager(){
    }
    public static ImageLoaderManager getInstance(){
        return INSTANCE;
    }

    public  void setImageLoaderStrategy(IImageLoaderstrategy strategy){
        loaderstrategy=strategy;
    }

    /*
     *   可创建默认的Options设置，假如不需要使用ImageView ，
     *    请自行new一个Imageview传入即可
     *  内部只需要获取Context
     */
    public static ImageLoaderOptions getDefaultOptions(@NonNull View container, @NonNull String url){
        return new ImageLoaderOptions.Builder(container,url).isCrossFade(true).build();
    }

    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {
        if (loaderstrategy != null) {
            loaderstrategy.showImage(options);
        }
    }

    public void showImage(View container, String url) {
        showImage(getDefaultOptions(container,url));
    }


    @Override
    public void cleanMemory(Context context) {
        if (loaderstrategy != null){
            loaderstrategy.cleanMemory(context);
        }
    }


}
