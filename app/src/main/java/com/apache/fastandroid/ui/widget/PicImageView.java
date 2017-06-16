package com.apache.fastandroid.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class PicImageView extends android.support.v7.widget.AppCompatImageView {
    public static final String TAG = PicImageView.class.getSimpleName();
    public PicImageView(Context context) {
        this(context,null);
        init();
    }

    public PicImageView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
        init();
    }

    public PicImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {
    }


    private int imageWidth;
    private int imageHeight;

    public void setImageWidth(int w) {
        imageWidth = w;
    }

    public void setImageHeight(int h) {
        imageHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //NLog.d(TAG, "onMeasure width = %s, height = %s, imageHeight = %s", width,height,imageHeight);
        if (imageWidth == 0){
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            return;
        }
        int iw = imageWidth;
        int ih = imageHeight;
        int heightC = width * ih / iw;
        NLog.d(TAG, "width = %s, height = %s, iw = %s, ih = %s, heightC = %s",width,height,iw,ih,heightC);
        if (height > 0){
            if (heightC > height){
                heightC = height;
                width = heightC * iw / ih;
            }
        }
        setScaleType(ScaleType.CENTER_CROP);
        setMeasuredDimension(width, heightC);
    }
}
