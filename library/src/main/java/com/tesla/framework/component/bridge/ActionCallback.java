package com.tesla.framework.component.bridge;

/**
 * Created by 01370340 on 2017/9/4.
 */

public  class ActionCallback implements IActionDelegate.IActionCallback {

    @Override
    public void onActionPrepare() {
        //donothing
    }

    @Override
    public void onActionSuccess(Object... result) {
        //donothing
    }

    @Override
    public void onActionFailed(int code, String msg) {
        //donothing
    }


    @Override
    public void onActionFinish() {
        //donothing
    }


}
