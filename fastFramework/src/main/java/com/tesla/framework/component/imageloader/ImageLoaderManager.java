package com.tesla.framework.component.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.tesla.framework.applike.FrameworkApplication;

import androidx.annotation.NonNull;


/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class ImageLoaderManager implements IImageLoaderstrategy,IImagePahtFromCache {
    private static final ImageLoaderManager INSTANCE = new ImageLoaderManager();

    private  IImageLoaderstrategy loaderstrategy;

    private  ImageLoaderOptions options;

    /**
     * 如果需要设置一些默认的参数,可以在这里设置
     * @param options
     */
    public void setOptions(ImageLoaderOptions options) {
        this.options = options;
    }

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
    public static ImageLoaderOptions getDefaultOptions(@NonNull View container, @NonNull String url,Context context){
        //return new ImageLoaderOptions.Builder(container,url,context).isCrossFade(true).build();
        return ImageLoaderOptions.obtainBuilder(null).container(container).context(context).url(url).corssFade(true).build();
    }

    /**
     *
     * @param options 外面传入options参数
     */
    @Override
    public void showImage(@NonNull ImageLoaderOptions options) {
        if (loaderstrategy != null) {
            loaderstrategy.showImage(options);
        }
    }

    public void showImage(View container, String url,Context context) {
        ImageLoaderOptions  options = ImageLoaderOptions.obtainBuilder(null).container(container).context(context).url(url).corssFade(true).build();
        showImage(options);
    }

    public void showImage(View container, String url) {
        showImage(container,url, FrameworkApplication.getContext());
    }

    @Override
    public void cleanMemory(Context context) {
        if (loaderstrategy != null){
            loaderstrategy.cleanMemory(context);
        }
    }


    @Override
    public void init(Context context) {
        if (loaderstrategy != null){
            loaderstrategy.init(context);
        }
    }

    @Override
    public void pause(Context context) {

    }

    @Override
    public void resume(Context context) {

    }

    @Override
    public String getImagePahtFromCache(String url) {
        if (loaderstrategy != null && loaderstrategy instanceof IImagePahtFromCache){
            IImagePahtFromCache iImagePahtFromCache = (IImagePahtFromCache) loaderstrategy;
            return iImagePahtFromCache.getImagePahtFromCache(url);
        }
        return null;
    }

    @Override
    public Bitmap getBitmapFromCache(String url) {
        if (loaderstrategy != null && loaderstrategy instanceof IImagePahtFromCache){
            IImagePahtFromCache iImagePahtFromCache = (IImagePahtFromCache) loaderstrategy;
            return iImagePahtFromCache.getBitmapFromCache(url);
        }
        return null;
    }
}
