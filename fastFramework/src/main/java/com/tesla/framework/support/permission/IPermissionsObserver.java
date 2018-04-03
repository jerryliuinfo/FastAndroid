package com.tesla.framework.support.permission;


public interface IPermissionsObserver {

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

}
