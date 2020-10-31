package com.tesla.framework.common.util.view.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * Created by jerryliu on 2017/6/4.
 */

public class DrawableCompatUtil {
    public static Drawable tintDrawable(Context context,Drawable drawable, @ColorRes int colors) {
        return tintDrawable(drawable, ContextCompat.getColorStateList(context, colors));
    }


    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
