package com.tesla.framework.component.imageloader;

import android.graphics.Bitmap;

/**
 * Created by 01370340 on 2017/11/21.
 */

public interface IImagePahtFromCache {

    String getImagePahtFromCache(String url);

    Bitmap getBitmapFromCache(String url);
}
