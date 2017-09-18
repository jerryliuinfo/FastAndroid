package com.apache.fastandroid.delegate;

import android.os.Bundle;

import com.apache.fastandroid.MainTabsFragment;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.comBridge.IObjectDataDelegate;

/**
 * Created by 01370340 on 2017/9/18.
 */

public class GetTabsFragment implements IObjectDataDelegate {
    @Override
    public Object getObjectData(Bundle args, IActionDelegate.IActionCallback callback, Object... extras) {
        return MainTabsFragment.newFragment();
    }
}
