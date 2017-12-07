package com.apache.fastandroid.user.ui.contract;

import android.app.Activity;

import com.apache.fastandroid.artemis.mvp.BaseContract;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.tesla.framework.component.bridge.IActionDelegate;

/**
 * Created by 01370340 on 2017/12/7.
 */

public interface LoginContract {

     interface View extends BaseContract.BaseView{
        void showLoginSuccess(UserDetail userDetail);
    }

     interface Presenter extends BaseContract.BasePresenter<View> {

        void doAutoLogin(Activity activity,IActionDelegate.IActionCallback callback);

        void doLogin(String userName, String pwd, IActionDelegate.IActionCallback callback);
    }
}
