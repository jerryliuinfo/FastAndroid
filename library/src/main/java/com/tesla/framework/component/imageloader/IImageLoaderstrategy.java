package com.tesla.framework.component.imageloader;

import android.content.Context;
import android.support.annotation.NonNull;


/**
 * Created by Administrator on 2017/3/20 0020.
 */

public interface IImageLoaderstrategy {

    void showImage(@NonNull ImageLoaderOptions options);

    void cleanMemory(Context context);




}
