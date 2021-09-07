package com.apache.fastandroid.artemis.base;

import android.app.Activity;
import android.content.Intent;

import com.apache.fastandroid.artemis.AppContext;
import com.tesla.framework.support.action.IAction;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew;
import com.tesla.framework.ui.fragment.BaseLifecycleFragment;

import androidx.fragment.app.FragmentActivity;

/**
 * Created by jerryliu on 2017/6/6.
 * Fragment是一个神器，是跨Activity和Fragment之前通讯的重要的桥梁
 */

public class BizFragment extends BaseStatusFragmentNew {
    private FragmentActivity mActivity;
    private static final String BIZ_FRAGMENT_TAG = "com.apache.fastandroid.base.BizFragment";
    private Activity getRealActivity() {
        if (getActivity() != null){
            return getActivity();
        }

        return mActivity;
    }
    static BizFragment get(FragmentActivity activity) {
        return (BizFragment) activity.getSupportFragmentManager().findFragmentByTag(BIZ_FRAGMENT_TAG);
    }



    @Override
    public int inflateContentView() {
        return -1;
    }

    public static BizFragment createBizFragment(BaseLifecycleFragment fragment){
        if (fragment != null && fragment.getActivity() != null){
            BizFragment bizFragment = (BizFragment) fragment.getActivity().getSupportFragmentManager().findFragmentByTag(BIZ_FRAGMENT_TAG);
            if (bizFragment == null){
                bizFragment = new BizFragment();
                fragment.getActivity().getSupportFragmentManager().
                        beginTransaction().add(bizFragment,BIZ_FRAGMENT_TAG).commitAllowingStateLoss();
            }
            return bizFragment;
        }
        return null;
    }

    public static BizFragment createBizFragment(FragmentActivity activity) {
        BizFragment bizFragment = (BizFragment) activity.getSupportFragmentManager().findFragmentByTag(BIZ_FRAGMENT_TAG);
        if (bizFragment == null) {
            bizFragment = new BizFragment();
            bizFragment.mActivity = activity;

            if (activity instanceof BaseActivity && ((BaseActivity) activity).isDestroyed()) {
                return bizFragment;
            }

            activity.getSupportFragmentManager().beginTransaction().add(bizFragment, BIZ_FRAGMENT_TAG).commit();
        }
        return bizFragment;
    }

    public static final int REQUEST_CODE_LOGIN_ACTIVITY = 1000;
    private LoginAction requestWebLoginAction;
    class LoginAction extends IAction{

        @Override
        public boolean interrupt() {
            boolean interupted = AppContext.isNotLogined();
            if (interupted){
                doInterrupt();
            }else {
                requestWebLoginAction = null;
            }
            return interupted;
        }

        @Override
        public void doInterrupt() {
            requestWebLoginAction = this;
        }

        public LoginAction(Activity context, IAction parent) {
            super(context, parent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN_ACTIVITY){
            if (requestWebLoginAction != null){
                requestWebLoginAction.run();
            }
        }
    }
}
