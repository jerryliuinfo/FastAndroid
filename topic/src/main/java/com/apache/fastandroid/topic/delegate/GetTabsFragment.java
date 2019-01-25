package com.apache.fastandroid.topic.delegate;

import android.os.Bundle;

import com.apache.fastandroid.topic.MainTabsFragment;
import com.tesla.framework.component.bridge.IActionDelegate;
import com.tesla.framework.component.bridge.IObjectDataDelegate;

/**
 * Created by 01370340 on 2017/9/18.
 */

public class GetTabsFragment implements IObjectDataDelegate {
    @Override
    public Object getObjectData(Bundle args, IActionDelegate.IActionCallback callback, Object... extras) {
        return MainTabsFragment.newFragment();
    }
}
