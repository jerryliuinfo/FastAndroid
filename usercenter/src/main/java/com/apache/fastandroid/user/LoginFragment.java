package com.apache.fastandroid.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.apache.fastandroid.artemis.base.BaseFragment;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserBean;
import com.apache.fastandroid.user.sdk.UserSDK;
import com.apache.fastandroid.usercenter.R;
import com.tesla.framework.common.util.KeyGenerator;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.common.util.view.ViewUtils;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.FragmentContainerActivity;

/**
 * Created by jerryliu on 2017/7/9.
 */

public class LoginFragment extends BaseFragment {
    @ViewInject(idStr = "inputLayout_username")
    private TextInputLayout mUserNameinputLayout;

    @ViewInject(idStr = "inputLayout_pwd")
    private TextInputLayout mPwdInputLayout;

    @ViewInject(idStr = "btn_login")
    private Button btn_login;


    public static void start(Context from) {
        Intent intent = new Intent(from, FragmentContainerActivity.class);
        intent.putExtra("className", LoginFragment.class.getName());

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        from.startActivity(intent);


        //FragmentContainerActivity.launch(from,LoginFragment.class,null);
    }


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

        setToolbarTitle("");

        mUserNameinputLayout.setHint("UserName");
        mPwdInputLayout.setHint("Password");

        mUserNameinputLayout.getEditText().setText("liuxiangxiang1234@163.com");
        mPwdInputLayout.getEditText().setText("gree3502");
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
        if (checkUserNameAndPwdInvalidaty(userName,pwd)){
            new LoginTask().execute(userName,pwd);
        }
    }


    private boolean checkUserNameAndPwdInvalidaty(String userName,String pwd){
        if (TextUtils.isEmpty(userName)){
            mUserNameinputLayout.setError("用户名不合法");
            return false;
        }else if (TextUtils.isEmpty(pwd)){
            mUserNameinputLayout.setError("密码不合法");
            return false;
        }else {
            mUserNameinputLayout.setErrorEnabled(false);
            mPwdInputLayout.setErrorEnabled(false);
        }
        return true;
    }



    class LoginTask extends WorkTask<String,Void,Token>{

        @Override
        protected void onPrepare() {
            super.onPrepare();
            ViewUtils.createProgressDialog(getActivity(),"正在登录中...",0).show();
        }

        @Override
        public Token workInBackground(String... params) throws TaskException {

            boolean result = false;
            Token token = null;
            try {
                token = UserSDK.newInstance().login(params[0],params[1]);
                NLog.d(TAG, "token = %s", token);
                if (token != null){
                    result = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (result){
                return token;
            }
            throw new TaskException("Get token error");

        }

        @Override
        protected void onSuccess(Token token) {
            super.onSuccess(token);
            loginSuccess(token);
        }


        @Override
        protected void onFailure(TaskException exception) {
            super.onFailure(exception);
            loginFailed(exception);
        }

        @Override
        protected void onFinished() {
            super.onFinished();
            ViewUtils.dismissProgressDialog();
        }
    }


    public void loginSuccess(Token token) {
        showMessage("登录成功");


        UserBean userBean = new UserBean();
        userBean.setUserName(mUserNameinputLayout.getEditText().getText().toString());
        userBean.setPassword(mPwdInputLayout.getEditText().getText().toString());
        UserConfigManager.getInstance().saveUserBean(userBean);
        UserConfigManager.getInstance().saveToken(token);


        //MainActivity.launch(getActivity());
        Object[] extras = new Object[]{getActivity()};
        try {
            ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:moduleMain:startMainActivity",null,null,extras);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().finish();
    }

    public void loginSuccess(UserBean userBean) {
        showMessage("登录成功");
        //保存登陆信息
        String encrypedPwd = KeyGenerator.generateMD5(userBean.getPassword());

        UserConfigManager.getInstance().saveUserBean(userBean);



        //MainActivity.launch(getActivity());
        Object[] extras = new Object[]{getActivity()};
        try {
            ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:moduleMain:startMainActivity",null,null,extras);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().finish();
    }


    public void loginFailed(TaskException exception) {
        showMessage(exception.getMessage());
    }


}
