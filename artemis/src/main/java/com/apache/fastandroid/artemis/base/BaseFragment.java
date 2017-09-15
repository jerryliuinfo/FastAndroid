package com.apache.fastandroid.artemis.base;

import android.os.Bundle;

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
        if (compositeSubscription != null){
            compositeSubscription.clear();
        }
    }

    public CompositeSubscription getCompositeSubscription() {
        return compositeSubscription;
    }


}
