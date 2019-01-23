package com.tesla.framework.component.permission;


public interface IPermissionsSubject {

    void attach(IPermissionsObserver observer);

    void detach(IPermissionsObserver observer);

    void notifyActivityResult(int requestCode, String[] permissions, int[] grantResults);

}
