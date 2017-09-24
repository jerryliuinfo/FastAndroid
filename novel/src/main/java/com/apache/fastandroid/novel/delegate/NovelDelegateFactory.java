package com.apache.fastandroid.novel.delegate;

import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.comBridge.IDataDelegate;
import com.apache.fastandroid.artemis.comBridge.IDelegateFactory;
import com.apache.fastandroid.artemis.comBridge.IObjectDataDelegate;
import com.apache.fastandroid.novel.delegate.impl.GetNovelHomeTabsFragment;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class NovelDelegateFactory implements IDelegateFactory {

    public static final String ACTION_GET_HOME_TABS_FRAGMENT = "getMainTabsFragment";

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
            case ACTION_GET_HOME_TABS_FRAGMENT:
                return new GetNovelHomeTabsFragment();
        }
        return null;
    }
}
