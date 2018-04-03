package com.tesla.framework.common.util.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import java.io.InputStream;

/**
 * Created by 01370340 on 2018/3/21.
 */

public class LargeBitmapUtil {


    public static Bitmap decodeRegion(InputStream inputStream, Rect rect) {
        try {

            final BitmapFactory.Options options = new BitmapFactory.Options();
            //第一次解析将inJustDecodeBounds设置为true，来获取图片大小
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream,null,options);

            options.inSampleSize = calculateInSampleSize(options,rect.width(),rect.height());
            BitmapRegionDecoder bitmapDecoder = BitmapRegionDecoder.newInstance(inputStream,true);
            return bitmapDecoder.decodeRegion(rect, options).copy(Bitmap.Config.RGB_565, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    public static Bitmap decodeSampledBitmap(Resources res, int resId, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        //第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);


        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }



    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 获得内存中图片的宽高
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // 计算出一个数值，必须符合为2的幂（1，2，4，8，tec），赋值给inSampleSize
            // 图片宽高应大于期望的宽高的时候，才进行计算
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


}
