package com.tesla.framework.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.tesla.framework.applike.FrameworkApplication;


public class ToastUtils {

    private static Toast mToast;
    private static Handler sUiHandler = new Handler(Looper.getMainLooper());



    /********************** 非连续弹出的Toast ***********************/
    public static void showSingleToast(int resId) { //R.string.**
        showSingleToast(FrameworkApplication.getContext().getString(resId));
    }

    public static void showSingleToast(String text) {
        getSingleToast(text, Toast.LENGTH_SHORT).show();
        final Toast toast = getSingleToast(text, Toast.LENGTH_SHORT);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()){
            toast.show();
        }else{
            sUiHandler.post(new Runnable() {
                @Override
                public void run() {
                    toast.show();
                }
            });
        }
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


    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int messageId) {
        if (context != null) {
            Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
        }
    }

}
