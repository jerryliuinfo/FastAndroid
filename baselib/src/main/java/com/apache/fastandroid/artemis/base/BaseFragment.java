package com.apache.fastandroid.artemis.base;

import android.app.Activity;
import android.os.Bundle;
import com.tesla.framework.ui.activity.BaseActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;


/**
 * Created by 01370340 on 2017/9/1.
 */

public abstract class BaseFragment extends ABaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (getActivity() != null && getActivity() instanceof BaseActivity){
            Activity activity = getActivity();
            //监听activity内存泄漏
        }

    }



}
