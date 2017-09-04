package com.apache.fastandroid.user.delegate.impl;

import android.os.Bundle;

import com.apache.fastandroid.artemis.CacheUtil;
import com.apache.fastandroid.artemis.comBridge.DelegateException;
import com.apache.fastandroid.artemis.comBridge.IDataDelegate;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class GetToken implements IDataDelegate {
    @Override
    public Bundle getData(Bundle args, Object... extras) throws DelegateException {
        Bundle result = new Bundle();
        result.putSerializable("token", CacheUtil.getToken());
        return result;
    }
}
