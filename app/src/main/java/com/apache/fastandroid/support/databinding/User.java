package com.apache.fastandroid.support.databinding;

import android.view.View;

import com.tesla.framework.common.util.log.NLog;

import java.io.Serializable;

/**
 * Created by jerryliu on 2017/7/6.
 */

public class User implements Serializable {
    public static final String TAG = User.class.getSimpleName();
    public final String firstName;
    public final String lastName;
    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void onClickName(View view) {
        NLog.d(TAG, "onClickName ");
    }
    public void onClickmore(View view) {
        NLog.d(TAG, "onClickmore ");
    }
}
