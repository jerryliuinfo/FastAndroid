package com.tesla.framework.component.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;


/**
 * Created by Administrator on 2017/3/20 0020.
 */

public interface IImageLoaderstrategy {

    void showImage(@NonNull ImageLoaderOptions options);

    void cleanMemory(Context context);

    /**
     * 有些图片框架显示图片需要做一些初始化配置
     * @param context
     */
    void init(Context context);

    void pause(Context context);

    void resume(Context context);



}
