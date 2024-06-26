package com.apache.fastandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;

import androidx.annotation.Nullable;

/**
 * author: 01370340
 * data: 2019-10-14
 * description:
 */
public class AnrView extends View {
    private Context mContext;
    private Paint mPaint;
    public AnrView(Context context) {
        this(context,null);
    }

    private void init(Context context){
        this.mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(40);
    }

    public AnrView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 100000; i++) {
            canvas.drawText("jerry", ScreenUtils.getScreenWidth()/2, BarUtils.getActionBarHeight() /2,mPaint);
        }
    }
}
