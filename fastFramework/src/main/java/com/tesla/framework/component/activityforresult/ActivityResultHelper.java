package com.tesla.framework.component.activityforresult;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;

/**
 * Created by 01370340 on 2018/12/4.
 */
public class ActivityResultHelper {
    private static final String TAG = ActivityForResultRouterFragment.class.getName();

    private ActivityForResultRouterFragment mRouterFragment;

    private Activity mActivity;
    private ActivityResultHelper(Activity activity) {
        this.mActivity = activity;
        mRouterFragment = getRouterFragment(mActivity);
    }

    public static ActivityResultHelper newInstance(Activity activity){
        return new ActivityResultHelper(activity);
    }

    public void startActivityForResult(Class<?> clz, ActivityForResultCallback callback ){
        Intent intent = new Intent(mActivity,clz);
        startActivityForResult(intent,callback);
    }

    public void startActivityForResult(Intent intent, ActivityForResultCallback callback ){
        mRouterFragment.startActivityForResult(intent,callback);
    }

    private ActivityForResultRouterFragment getRouterFragment(Activity activity){
        FragmentManager fragmentManager = activity.getFragmentManager();
        ActivityForResultRouterFragment fragment = (ActivityForResultRouterFragment) findFragmentByTag(activity);
        if (fragment == null){
            fragment = ActivityForResultRouterFragment.newInstance();
            fragmentManager.beginTransaction().add(fragment,TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }

    public Fragment findFragmentByTag(Activity activity){
        return  activity.getFragmentManager().findFragmentByTag(TAG);
    }


    public interface ActivityForResultCallback {

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
