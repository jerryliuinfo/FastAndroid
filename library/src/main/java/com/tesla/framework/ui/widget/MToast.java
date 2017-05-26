package com.tesla.framework.ui.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangdan on 15/4/15.
 */
public class MToast {

    public static void showMessage(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

}
