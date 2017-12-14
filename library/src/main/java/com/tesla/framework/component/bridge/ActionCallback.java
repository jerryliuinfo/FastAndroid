package com.tesla.framework.component.bridge;

/**
 * Created by 01370340 on 2017/9/4.
 */

public  class ActionCallback implements IActionDelegate.IActionCallback {

    @Override
    public void onActionPrepare() {
        //提供默认的空实现
    }

    @Override
    public void onActionSuccess(Object... result) {
        //提供默认的空实现
    }

    @Override
    public void onActionFailed(int code, String msg) {
        //提供默认的空实现
    }


    @Override
    public void onActionFinish() {
        //提供默认的空实现
    }
}
