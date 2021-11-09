package com.apache.fastandroid.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

/**
 * Created by Jerry on 2021/11/3.
 */
class TclAuthorizationDialog extends Dialog {


    public TclAuthorizationDialog(@NonNull Context context) {
        this(context,0);
    }

    public TclAuthorizationDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}
