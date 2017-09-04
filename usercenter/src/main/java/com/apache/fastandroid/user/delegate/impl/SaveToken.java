package com.apache.fastandroid.user.delegate.impl;

import android.os.Bundle;

import com.apache.fastandroid.artemis.CacheUtil;
import com.apache.fastandroid.artemis.comBridge.DelegateException;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.support.bean.Token;

/**
 * Created by 01370340 on 2017/9/3.
 */

public class SaveToken implements IActionDelegate {
    @Override
    public void runAction(Bundle args, IActionCallback callback, Object... extras) throws DelegateException {

        if (args != null){
            Token token = (Token) args.getSerializable("token");
            if (token != null){
                CacheUtil.saveToken(token);

            }
        }
    }
}
