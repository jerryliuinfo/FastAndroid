package com.apache.fastandroid.support.presenter;

import com.apache.fastandroid.mvp.MvpView;
import com.apache.fastandroid.support.bean.UserBean;

/**
 * Created by jerryliu on 2017/7/9.
 */

public interface LoginView extends MvpView {
    void loginSuccess(UserBean userBean);
    void loginFailed(String code,String msg);
}
