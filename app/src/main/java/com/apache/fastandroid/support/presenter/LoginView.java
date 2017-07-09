package com.apache.fastandroid.support.presenter;

import com.apache.fastandroid.support.bean.UserBean;
import com.apache.fastandroid.ui.mvp.base.MvpView;

/**
 * Created by jerryliu on 2017/7/9.
 */

public interface LoginView extends MvpView{
    void loginSuccess(UserBean userBean);
    void loginFailed(String code,String msg);
}
