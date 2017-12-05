package com.apache.fastandroid.delegate.impl;

import android.os.Bundle;

import com.tesla.framework.component.bridge.DelegateException;
import com.tesla.framework.component.bridge.IDataDelegate;

/**
 * Created by 01370340 on 2017/12/5.
 */

public class ModuleUsercenterFactoryName implements IDataDelegate {
    @Override
    public Bundle getData(Bundle args, Object... extras) throws DelegateException {
        Bundle bundle = new Bundle();
        bundle.putString("result","com.apache.fastandroid.user.delegate.UsercenterDelegateFactory");
        return bundle;
    }
}
