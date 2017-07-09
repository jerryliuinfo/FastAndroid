package com.apache.fastandroid.support.presenter;

import android.os.Handler;

import com.apache.fastandroid.support.bean.UserBean;
import com.apache.fastandroid.ui.fragment.user.LoginFragment;
import com.apache.fastandroid.ui.mvp.base.BaseMvpPresenter;

/**
 * Created by jerryliu on 2017/7/9.
 */

public class LoginPresenterImpl extends BaseMvpPresenter<LoginFragment> implements LoginPresenter {

    @Override
    public void login(final String userName, final String password) {
        final LoginView loginView = getView();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loginView==null)
                    return;
                if ("zhangsan".equals(userName) && "123456".equals(password))
                {
                    UserBean userBean = new UserBean("zhangsan","123456");
                    loginView.loginSuccess(userBean);
                }
                else
                {
                    loginView.loginFailed("500","inner error");
                }
            }
        },1000);
    }
}
