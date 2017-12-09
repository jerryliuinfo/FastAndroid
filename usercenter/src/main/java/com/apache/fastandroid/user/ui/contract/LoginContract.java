package com.apache.fastandroid.user.ui.contract;

import android.app.Activity;

import com.apache.fastandroid.artemis.mvp.BaseContract;
import com.apache.fastandroid.artemis.support.bean.UserDetail;

/**
 * Created by 01370340 on 2017/12/7.
 */

public interface LoginContract {

    interface View extends BaseContract.BaseView{
        void showLoginSuccess(UserDetail userDetail);
    }

     interface Presenter extends BaseContract.BasePresenter<View> {

        void doAutoLogin(Activity activityk);

        void doLogin(String userName, String pwd);
    }
}
