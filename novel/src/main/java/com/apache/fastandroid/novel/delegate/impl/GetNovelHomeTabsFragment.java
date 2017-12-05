package com.apache.fastandroid.novel.delegate.impl;

import android.os.Bundle;

import com.apache.fastandroid.artemis.bridge.IActionDelegate;
import com.apache.fastandroid.artemis.bridge.IObjectDataDelegate;
import com.apache.fastandroid.novel.NovelHomeTabsFragment;

/**
 * Created by 01370340 on 2017/9/24.
 */

public class GetNovelHomeTabsFragment implements IObjectDataDelegate {
    @Override
    public Object getObjectData(Bundle args, IActionDelegate.IActionCallback callback, Object... extras) {
        return NovelHomeTabsFragment.newFragment();
    }
}
