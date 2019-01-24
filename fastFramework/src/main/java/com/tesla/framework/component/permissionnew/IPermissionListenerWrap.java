package com.tesla.framework.component.permissionnew;

/**
 * Created by Jerry on 2019/1/23.
 */
public interface IPermissionListenerWrap {

    interface IEachPermissionListener{

        void onAccepted(Permission permission);

        void onException(Throwable throwable);
    }

    interface IPermissionsHelper{

        void onAccepted(boolean isGranted);

        void onException(Throwable throwable);
    }
}
