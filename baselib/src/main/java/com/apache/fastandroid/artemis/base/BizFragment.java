package com.apache.fastandroid.artemis.base;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;

import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;

/**
 * Created by jerryliu on 2017/6/6.
 * Fragment是一个神器，是跨Activity和Fragment之前通讯的重要的桥梁
 */

public class BizFragment extends ABaseFragment {
    private Activity mActivity;

    private Activity getRealActivity() {
        if (getActivity() != null)
            return getActivity();

        return mActivity;
    }

    private String getRealString(int resId) {
        if (getActivity() != null && getResources() != null) {
            return getString(resId);
        }

        return mActivity.getString(resId);
    }

    @Override
    public int inflateContentView() {
        return -1;
    }

    public static BizFragment createBizFragment(ABaseFragment fragment){
        if (fragment != null && fragment.getActivity() != null){
            String fragmentTag = "com.apache.fastandroid.base.BizFragment";
            BizFragment bizFragment = (BizFragment) fragment.getActivity().getSupportFragmentManager().findFragmentByTag(fragmentTag);
            if (bizFragment == null){
                bizFragment = new BizFragment();
                fragment.getActivity().getSupportFragmentManager().
                        beginTransaction().add(bizFragment,fragmentTag).commitAllowingStateLoss();
            }
            return bizFragment;
        }
        return null;
    }

    public static BizFragment createBizFragment(FragmentActivity activity) {
        BizFragment bizFragment = (BizFragment) activity.getSupportFragmentManager().findFragmentByTag("BizFragment");
        if (bizFragment == null) {
            bizFragment = new BizFragment();
            bizFragment.mActivity = activity;

            if (activity instanceof BaseActivity && ((BaseActivity) activity).isDestory()) {
                return bizFragment;
            }

            activity.getSupportFragmentManager().beginTransaction().add(bizFragment, "BizFragment").commit();
        }
        return bizFragment;
    }


}
