package com.apache.fastandroid.delegate;

import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.comBridge.IDataDelegate;
import com.apache.fastandroid.artemis.comBridge.IDelegateFactory;
import com.apache.fastandroid.artemis.comBridge.IObjectDataDelegate;

/**
 * Created by 01370340 on 2017/9/18.
 */

public class TopicDelegateFactory implements IDelegateFactory {

    public static final String ACTION_GET_MAIN_TABLS_FRAGMENT = "getMainTabsFragment";
    @Override
    public IDataDelegate getDataTransfer(String action) {
        return null;
    }

    @Override
    public IActionDelegate getActionTransfer(String action) {
        return null;
    }

    @Override
    public IObjectDataDelegate getObjectDataTransfer(String action) {
        switch (action){
            case ACTION_GET_MAIN_TABLS_FRAGMENT:
                return new GetTabsFragment();
        }
        return null;
    }
}
