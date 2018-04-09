package com.tesla.framework.ui.widget;

import android.content.Context;
import android.widget.Toast;

import com.tesla.framework.applike.FrameworkApplication;


public class ToastUtils {

    private static Toast mToast;


    /********************** 非连续弹出的Toast ***********************/
    public static void showSingleToast(int resId) { //R.string.**
        showSingleToast(FrameworkApplication.getContext().getString(resId));
    }

    public static void showSingleToast(String text) {
        getSingleToast(text, Toast.LENGTH_SHORT).show();
    }

    private static Toast getSingleToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(FrameworkApplication.getContext(), text, duration);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    /********************** 非连续弹出的Toast ***********************/


    public static void showMessage(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showMessage(Context context, int messageId) {
        if (context != null) {
            Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
        }
    }

}
