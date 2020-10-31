package com.tesla.framework.component.permissionnew;

import androidx.annotation.NonNull;

/**
 * Created by Jerry on 2019/1/23.
 */
public interface IPermissionListenerWrap {


    interface IPermissionsHelper{
        void onPermissionGranted(String[] permissions);

        void onPermissionDenied(String[] permissions,@NonNull int[] grantResults,boolean[] alwaysDenied);
    }
}
