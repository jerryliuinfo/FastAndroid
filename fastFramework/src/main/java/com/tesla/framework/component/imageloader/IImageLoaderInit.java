package com.tesla.framework.component.imageloader;

import android.content.Context;

/**
 * Created by 01370340 on 2017/11/18.
 */

public interface IImageLoaderInit {
    /**
     * 有些图片框架显示图片需要做一些初始化配置
     * @param context
     */
    void init(Context context);
}
