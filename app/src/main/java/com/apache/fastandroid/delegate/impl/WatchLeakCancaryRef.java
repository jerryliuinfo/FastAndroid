package com.apache.fastandroid.delegate.impl;

import android.app.Activity;
import android.os.Bundle;

import com.apache.fastandroid.app.MyApplication;
import com.apache.fastandroid.artemis.bridge.DelegateException;
import com.apache.fastandroid.artemis.bridge.IActionDelegate;

/**
 * Created by 01370340 on 2017/9/24.
 * 检测内存泄漏‰
 */

public class WatchLeakCancaryRef implements IActionDelegate {


    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {
        if (extras != null && extras.length > 0 && extras[0] instanceof Activity){
            Activity context = (Activity) extras[0];
            MyApplication.getRefWatcher(context).watch(context);
        }
    }
}
