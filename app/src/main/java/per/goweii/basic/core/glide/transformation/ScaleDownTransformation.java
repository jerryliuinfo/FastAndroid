package per.goweii.basic.core.glide.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * 描述：高斯模糊
 *
 * @author Cuizhen
 * @date 2018/9/13
 */
public class ScaleDownTransformation extends BitmapTransformation {

    private static final String ID = ScaleDownTransformation.class.getName();

    private final float maxWidth;
    private final float maxHeight;

    public ScaleDownTransformation() {
//        maxWidth = DisplayInfoUtils.getInstance().getWidthPixels();
        maxWidth = ScreenUtils.getScreenWidth();
        maxHeight = ScreenUtils.getScreenHeight();
    }

    private final Canvas mCanvas = new Canvas();

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform.getWidth() <= maxWidth && toTransform.getHeight() <= maxHeight) {
            return toTransform;
        }
        float sx = maxWidth / toTransform.getWidth();
        float sy = maxHeight / toTransform.getHeight();
        float s = Math.min(sx, sy);
        int sw = (int) (toTransform.getWidth() * s);
        int sh = (int) (toTransform.getHeight() * s);
        Bitmap output = pool.get(sw, sh, toTransform.getConfig());
        mCanvas.setBitmap(output);
        mCanvas.drawBitmap(toTransform,
                new Rect(0, 0, toTransform.getWidth(), toTransform.getHeight()),
                new Rect(0, 0, sw, sh),
                null);
        return output;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ScaleDownTransformation;
    }

    @Override
    public int hashCode() {
        return (ID.hashCode());
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID).getBytes(CHARSET));
    }
}
