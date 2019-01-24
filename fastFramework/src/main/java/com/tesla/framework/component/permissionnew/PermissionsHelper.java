package com.tesla.framework.component.permissionnew;

import android.support.v4.app.FragmentActivity;

/**
 * Created by Jerry on 2019/1/23.
 */
public class PermissionsHelper {
    private FragmentActivity mActivity;
    public PermissionsHelper(FragmentActivity activity){
        this.mActivity = activity;
    }


    public static PermissionsHelper newInstance(FragmentActivity activity){
        return new PermissionsHelper(activity);
    }
}
