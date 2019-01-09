package com.tesla.framework.component.activityforresult;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;

/**
 * Created by 01370340 on 2018/12/4.
 */
public class ActivityLauncher {
    public static final String TAG = "com.apache.book.activityforresult.ActivityLauncher";

    private RouterFragment mRouterFragment;

    private Activity mActivity;
    public ActivityLauncher(Activity activity) {
        this.mActivity = activity;
        mRouterFragment = getRouterFragment(mActivity);
    }

    public void startActivityForResult(Class<?> clz, Callback callback ){
        Intent intent = new Intent(mActivity,clz);
        startActivityForResult(intent,callback);
    }

    public void startActivityForResult(Intent intent, Callback callback ){
        mRouterFragment.startActivityForResult(intent,callback);
    }

    private RouterFragment getRouterFragment(Activity activity){
        FragmentManager fragmentManager = activity.getFragmentManager();
        RouterFragment fragment = (RouterFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null){
            fragment = RouterFragment.newInstance();
            fragmentManager.beginTransaction().add(fragment,TAG).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return fragment;
    }


    public interface Callback{

        void onActivityResult(int requestCode, int resultCode, Intent data);
    }
}
