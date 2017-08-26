package com.apache.fastandroid.user;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.apache.fastandroid.R;
import com.apache.fastandroid.support.bean.UserBean;
import com.apache.fastandroid.support.config.UserConfigManager;
import com.apache.fastandroid.support.presenter.LoginPresenterImpl;
import com.apache.fastandroid.support.presenter.LoginView;
import com.apache.fastandroid.MainActivity;
import com.apache.fastandroid.mvp.BaseMvpFragment;
import com.tesla.framework.common.util.KeyGenerator;
import com.tesla.framework.ui.activity.FragmentContainerActivity;

/**
 * Created by jerryliu on 2017/7/9.
 */

public class LoginFragment extends BaseMvpFragment<LoginPresenterImpl>
        implements LoginView{

    private TextInputLayout mUserNameinputLayout;
    private TextInputLayout mPwdInputLayout;
    private Button btn_login;

    public static void start(Activity from) {
        FragmentContainerActivity.launch(from,LoginFragment.class,null);
    }

    @Override
    public int inflateContentView() {
        return R.layout.frament_user_login;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        btn_login = (Button) findViewById(R.id.btn_login);

        mUserNameinputLayout = (TextInputLayout) findViewById(R.id.inputLayout_username);
        mPwdInputLayout = (TextInputLayout) findViewById(R.id.inputLayout_pwd);

        mUserNameinputLayout.setHint("UserName");
        mPwdInputLayout.setHint("Password");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               doLogin();
            }
        });
    }

    public void doLogin(){
        String userName = mUserNameinputLayout.getEditText().getText().toString();
        String pwd = mPwdInputLayout.getEditText().getText().toString();
        if (!validateUserName(userName)){
            mUserNameinputLayout.setError("用户名不合法");
            return;
        }else if (!validatePwd(pwd)){
            mPwdInputLayout.setError("密码不合法");
            return;
        }else {
            mUserNameinputLayout.setEnabled(false);
            mPwdInputLayout.setErrorEnabled(false);
            getPresenter().login(userName,pwd);
        }
    }


    private boolean validateUserName(String userName){
        if (TextUtils.isEmpty(userName)){
            return false;
        }
        return true;
    }

    private boolean validatePwd(String pwd){
        if (TextUtils.isEmpty(pwd)){
            return false;
        }
        return true;
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        showMessage("loginSuccess");
        //保存登陆信息
        String encypedUserName = KeyGenerator.generateMD5(userBean.userName);
        String encrypedPwd = KeyGenerator.generateMD5(userBean.password);

        UserConfigManager.getInstance().setUserName(encypedUserName);
        UserConfigManager.getInstance().setPwd(encrypedPwd);

        MainActivity.launch(getActivity());
        getActivity().finish();
    }

    @Override
    public void loginFailed(String code, String msg) {
        showMessage("loginFailed");
    }

    @Override
    public LoginPresenterImpl createPresenter() {
        return new LoginPresenterImpl();
    }
}
