package com.tesla.framework.component.permission;


public interface IPermissionsObserver {

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

}
