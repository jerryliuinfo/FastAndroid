package com.apache.fastandroid.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import com.apache.fastandroid.R;
import com.blankj.utilcode.util.ColorUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2021/6/25.
 */
public class LoadingDrawable extends Drawable {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap bitmap;

    private RectF mBounds;
    private int roundCorner;

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mBounds = new RectF(bounds);
    }

    public LoadingDrawable(Bitmap bitmap, int roundCorner) {
        this.bitmap = bitmap;
        paint.setColor(ColorUtils.getColor(R.color.color_33FF8610));
        this.roundCorner = roundCorner;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mBounds != null){
            canvas.drawRoundRect(mBounds,roundCorner,roundCorner, paint);
            canvas.drawBitmap(bitmap,getIntrinsicWidth() / 2 - bitmap.getWidth() /2, getIntrinsicHeight() / 2 - bitmap.getHeight() / 2,bitmapPaint);
        }
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mBounds.width();
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mBounds.height();
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
