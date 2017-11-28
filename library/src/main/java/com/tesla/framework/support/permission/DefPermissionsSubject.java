package com.tesla.framework.support.permission;

import java.util.ArrayList;
import java.util.List;


public class DefPermissionsSubject implements IPermissionsSubject {

    private List<IPermissionsObserver> observers;

    public DefPermissionsSubject() {
        observers = new ArrayList<>();
    }

    @Override
    public void attach(IPermissionsObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void detach(IPermissionsObserver observer) {
        if (observer != null && !observers.contains(observer))
            observers.remove(observer);
    }

    @Override
    public void notifyActivityResult(int requestCode, String[] permissions, int[] grantResults) {
        for (IPermissionsObserver observer : observers) {
            observer.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
