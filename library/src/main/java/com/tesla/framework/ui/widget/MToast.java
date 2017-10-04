package com.tesla.framework.ui.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangdan on 15/4/15.
 */
public class MToast {

    private static Toast mToast;


    /********************** 非连续弹出的Toast ***********************/
    public static void showSingleToast(Context context,int resId) { //R.string.**
        showSingleToast(context,context.getString(resId));
    }

    public static void showSingleToast(Context context,String text) {
        getSingleToast(context,text, Toast.LENGTH_SHORT).show();
    }

    private static Toast getSingleToast(Context context,String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
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
