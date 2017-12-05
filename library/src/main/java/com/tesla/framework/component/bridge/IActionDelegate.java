package com.tesla.framework.component.bridge;

import android.os.Bundle;

/**
 * Created by jerryliu on 2017/7/11.
 * 代码块(Action)接口协议，可以触发其他Module开放的功能性接口
 */

public interface IActionDelegate {

    /**
     * @param args
     * @param callback 请Module之间根据协议，回调这4个状态做相应的业务处理
     */
    void runAction(Bundle args, IActionCallback callback, Object... extras)throws DelegateException;


    interface IActionCallback{
        void onActionPrepare();
        void onActionSuccess(Object... result);
        void onActionFailed(int code, String msg);
        void onActionFinish();
    }
}
