package com.apache.fastandroid.user.ui.contract;

import android.app.Activity;

import com.apache.fastandroid.artemis.mvp.view.IView;
import com.apache.fastandroid.artemis.support.bean.UserDetail;

/**
 * Created by 01370340 on 2017/12/7.
 */

public interface LoginContractNew {
    interface Presenter  {

        void doAutoLogin(Activity activityk);

        void doLogin(String userName, String pwd);
    }

    interface View extends IView {
        void getUserInfo(UserDetail userDetail);
    }


}
