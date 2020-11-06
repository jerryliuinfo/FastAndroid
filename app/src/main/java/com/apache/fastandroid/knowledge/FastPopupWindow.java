package com.apache.fastandroid.knowledge;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

import com.apache.fastandroid.util.MainLogUtil;

import androidx.fragment.app.Fragment;

/**
 * author: jerry
 * created on: 2020/10/16 5:28 PM
 * description:
 */
class FastPopupWindow extends PopupWindow {
    private static final String TAG = "KaraokePopupWindow";

    public FastPopupWindow() {
        super();
    }
    public FastPopupWindow(Context context) {
        super(context);
    }

    public FastPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FastPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FastPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public FastPopupWindow(View contentView) {
        super(contentView);
    }

    public FastPopupWindow(int width, int height) {
        super(width, height);
    }
    public FastPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public FastPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public void showAsDropDown(View anchor) {
        try {
            super.showAsDropDown(anchor);
        } catch (Exception e) {
            MainLogUtil.e(TAG, "showAsDropDown error e = " + e.getCause());
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        try {
            super.showAsDropDown(anchor, xoff, yoff);
        } catch (Exception e) {
            MainLogUtil.e(TAG, "showAsDropDown error e = " + e.getCause());
        }
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        try {
            super.showAsDropDown(anchor, xoff, yoff, gravity);
        } catch (Exception e) {
            MainLogUtil.e(TAG, "showAsDropDown error e = " + e.getCause());
        }
    }

    public void showAtLocation(Fragment fragment, View parent, int gravity, int x, int y) {
        try {
            if (!isShowing() && isAlive(fragment) && parent != null && parent.getWindowToken() != null) {
                super.showAtLocation(parent, gravity, x, y);
            } else {
                MainLogUtil.e(TAG, "showAtLocation error");
            }
        } catch (Exception e) {
            MainLogUtil.e(TAG, "showAtLocation error e = " + e.getCause());
        }
    }

    public void showAtLocation(Activity activity, View parent, int gravity, int x, int y) {
        try {
            if (!isShowing() && isAlive(activity) && parent != null && parent.getWindowToken() != null) {
                super.showAtLocation(parent, gravity, x, y);
            } else {
                MainLogUtil.e(TAG, "showAtLocation error");
            }
        } catch (Exception e) {
            MainLogUtil.e(TAG, "showAtLocation error e = " + e.getCause());
        }
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        try {
            super.showAtLocation(parent, gravity, x, y);
        } catch (Exception e) {
            MainLogUtil.e(TAG, "showAtLocation error e = " + e.getCause());
        }
    }

    private boolean isAlive(Activity activity) {
        return activity != null && !activity.isFinishing();
    }

    private boolean isAlive(Fragment fragment) {
        if (fragment == null) {
            return false;
        }
        Activity activity = fragment.getActivity();
        return activity != null && !activity.isFinishing() && !fragment.isRemoving() && !fragment.isDetached() && fragment.isAdded();
    }

    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            MainLogUtil.e(TAG, "dismiss error e = " + e.getCause());
        }
    }
}
