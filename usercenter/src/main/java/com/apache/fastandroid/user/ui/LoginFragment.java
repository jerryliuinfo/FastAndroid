package com.apache.fastandroid.user.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.apache.fastandroid.artemis.CacheUtil;
import com.apache.fastandroid.artemis.base.BaseFragment;
import com.apache.fastandroid.artemis.comBridge.ModularizationDelegate;
import com.apache.fastandroid.artemis.support.bean.Token;
import com.apache.fastandroid.artemis.support.bean.UserDetail;
import com.apache.fastandroid.user.sdk.UserSDK;
import com.apache.fastandroid.user.support.cache.UserCache;
import com.apache.fastandroid.usercenter.R;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.FragmentContainerActivity;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


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
        final String userName = mUserNameinputLayout.getEditText().getText().toString();
        final String pwd = mPwdInputLayout.getEditText().getText().toString();

        if (checkUserNameAndPwdInvalidaty(userName,pwd)){
            Observable<Token> observable = UserSDK.newInstance().loginV2(userName,pwd);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Token>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            loginFailed(new TaskException(e.getMessage()));
                        }

                        @Override
                        public void onNext(Token token) {
                            loginSuccess(token);
                        }
                    });
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




    public void loginSuccess(Token token) {
        showMessage("登录成功");

//        UserBean userBean = new UserBean();
//        userBean.setUserName(mUserNameinputLayout.getEditText().getText().toString());
//        userBean.setPassword(mPwdInputLayout.getEditText().getText().toString());
        //UserConfigManager.getInstance().saveUserBean(userBean);
        //UserConfigManager.getInstance().saveToken(token);


        CacheUtil.saveToken(token);

        //MainActivity.launch(getActivity());
        try {
            ModularizationDelegate.getInstance().runStaticAction("com.apache.fastandroid:moduleMain:startMainActivity",null,null,new Object[]{getActivity()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().finish();


        //获取个人信息
        getMe();

    }


    private void getMe(){
        Observable<UserDetail> observable = UserSDK.newInstance().getMe();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        NLog.d(TAG, "getMe onError = %s", e);
                    }

                    @Override
                    public void onNext(UserDetail userDetail) {
                        NLog.d(TAG, "getMe onNext userDetail = %s", userDetail);

                        UserCache.saveMe(userDetail);
                    }


                });
    }



    public void loginFailed(TaskException exception) {
        showMessage(exception.getMessage());
    }


}
