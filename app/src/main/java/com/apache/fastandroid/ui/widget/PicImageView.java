package com.apache.fastandroid.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.tesla.framework.common.util.Logger;

/**
 * Created by jerryliu on 2017/4/11.
 */

public class PicImageView extends android.support.v7.widget.AppCompatImageView {
    public static final String TAG = PicImageView.class.getSimpleName();
    public PicImageView(Context context) {
        this(context,null);
    }

    public PicImageView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PicImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private boolean scaleToWidth = false; // this flag determines if should
    // measure height manually dependent
    // of width


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

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Logger.d(TAG, "onMeasure width = %s, height = %s, imageHeight = %s", width,height,imageHeight);
        if (imageHeight == 0){
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            return;
        }
        setMeasuredDimension(width, imageHeight);
    }
}
