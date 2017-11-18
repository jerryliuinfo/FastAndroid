package com.tesla.framework.component.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;


/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class ImageLoaderManager implements IImageLoaderstrategy,IImageLoaderInit {
    private static final ImageLoaderManager INSTANCE = new ImageLoaderManager();

    private  IImageLoaderstrategy loaderstrategy;

    ImageLoaderOptions options;

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
        return new ImageLoaderOptions.Builder(container,url,context).isCrossFade(true).build();
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
        if (options != null){
            options.setContext(context.getApplicationContext());
            options.setUrl(url);
            options.setViewContainer(container);
            showImage(options);
        }else {
            showImage(getDefaultOptions(container,url,context));
        }
    }


    @Override
    public void cleanMemory(Context context) {
        if (loaderstrategy != null){
            loaderstrategy.cleanMemory(context);
        }
    }


    @Override
    public void init(Context context) {
        if (loaderstrategy != null && loaderstrategy instanceof IImageLoaderInit){
            IImageLoaderInit imageLoaderInit = (IImageLoaderInit) loaderstrategy;
            imageLoaderInit.init(context);
        }
    }
}
