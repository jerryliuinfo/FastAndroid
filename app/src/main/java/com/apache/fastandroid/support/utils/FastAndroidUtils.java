package com.apache.fastandroid.support.utils;


import android.support.v4.app.Fragment;

import com.apache.fastandroid.MainActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;
import com.tesla.framework.ui.fragment.ATabsFragment;

/**
 * Created by jerryliu on 2017/6/4.
 */

public class FastAndroidUtils {

    /**
     * 检查某个Fragment是否是当前ATabsFragment选中的Framgent,用于懒加载功能实现
     * @param checkedFragment
     * @return
     */
    public static boolean checkTabsFragmentCanRequestData(Fragment checkedFragment) {
        if (checkedFragment.getActivity() == null)
            return false;

        ABaseFragment aFragment = null;
        if (checkedFragment.getActivity() instanceof MainActivity) {
            aFragment = (ABaseFragment) ((MainActivity) checkedFragment.getActivity()).getSupportFragmentManager().findFragmentByTag("MainFragment");
        }


        if (aFragment != null && aFragment instanceof ATabsFragment) {
            ATabsFragment fragment = (ATabsFragment) aFragment;
            return fragment.getCurrentFragment() == checkedFragment;
        }

        return false;
    }
}
