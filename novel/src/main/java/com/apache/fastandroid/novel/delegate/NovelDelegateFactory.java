package com.apache.fastandroid.novel.delegate;

import com.tesla.framework.component.bridge.IActionDelegate;
import com.tesla.framework.component.bridge.IDataDelegate;
import com.tesla.framework.component.bridge.IDelegateFactory;
import com.tesla.framework.component.bridge.IObjectDataDelegate;
import com.apache.fastandroid.novel.delegate.impl.GetBundleData;
import com.apache.fastandroid.novel.delegate.impl.GetNovelHomeTabsFragment;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class NovelDelegateFactory implements IDelegateFactory {

    public static final String ACTION_GET_HOME_TABS_FRAGMENT = "getMainTabsFragment";

    public static final String ACTION_GET_BUNDLE_DATA = "ACTION_GET_BUNDLE_DATA";

    @Override
    public IDataDelegate getDataTransfer(String action) {
        switch (action){
            case ACTION_GET_BUNDLE_DATA:
                return new GetBundleData();


        }
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
