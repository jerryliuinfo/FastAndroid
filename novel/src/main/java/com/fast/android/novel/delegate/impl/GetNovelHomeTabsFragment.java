package com.fast.android.novel.delegate.impl;

import android.os.Bundle;

import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.comBridge.IObjectDataDelegate;
import com.fast.android.novel.NovelHomeTabsFragment;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class GetNovelHomeTabsFragment implements IObjectDataDelegate {
    @Override
    public Object getObjectData(Bundle args, IActionDelegate.IActionCallback callback, Object... extras) {
        return NovelHomeTabsFragment.newFragment();
    }
}
