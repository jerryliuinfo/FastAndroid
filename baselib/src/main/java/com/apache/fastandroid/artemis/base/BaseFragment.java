package com.apache.fastandroid.artemis.base;

import android.app.Activity;
import android.os.Bundle;

import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.comBridge.ModuleConstans;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by 01370340 on 2017/9/1.
 */

public abstract class BaseFragment extends ABaseFragment {
    CompositeSubscription compositeSubscription;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getCompositeSubscription() != null){
            getCompositeSubscription().clear();
        }

        if (getActivity() != null && getActivity() instanceof BaseActivity){
            Activity activity = getActivity();
            //监听activity内存泄漏
            try {
                ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_MAIN_NAME+":watchLeakCancary",null,null,new Object[]{activity});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public CompositeSubscription getCompositeSubscription() {
        return compositeSubscription;
    }


}
