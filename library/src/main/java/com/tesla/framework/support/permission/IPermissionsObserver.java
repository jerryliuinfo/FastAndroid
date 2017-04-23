package com.tesla.framework.support.permission;

/**
 * Created by wangdan on 16/2/26.
 */
public interface IPermissionsObserver {

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

}
