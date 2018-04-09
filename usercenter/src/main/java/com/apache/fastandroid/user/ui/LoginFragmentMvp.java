package com.apache.fastandroid.user.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.apache.fastandroid.artemis.mvp.BaseMvpFragment;
import com.apache.fastandroid.user.ui.contract.LoginContractNew;
import com.apache.fastandroid.user.ui.presenter.LoginPresenterNew;
import com.apache.fastandroid.usercenter.R;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;


/**
 * Created by jerryliu on 2017/7/9.
 */

public class LoginFragmentMvp extends BaseMvpFragment<LoginPresenterNew,LoginContractNew.View> {
    @ViewInject(idStr = "inputLayout_username")
    private TextInputLayout mUserNameinputLayout;

    @ViewInject(idStr = "inputLayout_pwd")
    private TextInputLayout mPwdInputLayout;

    @ViewInject(idStr = "btn_login")
    private Button btn_login;
    
    
    public static final String EXTRA_KEY = "fromTopicDetail";

    private Boolean fromTopicDetail;
    public static void launch(Activity from, Boolean fromTopicDetail) {
        FragmentArgs args = new FragmentArgs();
        if (fromTopicDetail != null){
            args.add(EXTRA_KEY,fromTopicDetail);
        }
        FragmentContainerActivity.launch(from,LoginFragmentMvp.class,args);
    }

    @Override
    public int inflateContentView() {
        return R.layout.frament_user_login;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        setToolbarTitle("登录");
        if (savedInstanceSate == null && getArguments() != null && getArguments().containsKey(EXTRA_KEY)){
            fromTopicDetail = getArguments().getBoolean(EXTRA_KEY);
        }else {
            fromTopicDetail = savedInstanceSate.getBoolean(EXTRA_KEY);
        }

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fromTopicDetail != null){
            outState.putBoolean(EXTRA_KEY, fromTopicDetail);
        }
    }

    private void doLogin(){
        final String userName = mUserNameinputLayout.getEditText().getText().toString();
        String pwd = mPwdInputLayout.getEditText().getText().toString();
        if (checkUserNameAndPwdInvalidaty(userName,pwd)){
            getPresenter().doLogin(userName,pwd);
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

    @Override
    public LoginPresenterNew createPresenter() {
        return new LoginPresenterNew();
    }





   /* @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter();
    }


    @Override
    public void getUserInfo(UserDetail userDetail) {
        showMessage("登录成功");
        UserConfigManager.getInstance(getContext()).savePwd(mPwdInputLayout.getEditText().getText().toString());
        //从主题列表转转过来的话,则进入主题界面
        if (fromTopicDetail != null && fromTopicDetail){
            getActivity().finish();
        }else {
            try {
                ModularizationDelegate.getInstance().runStaticAction(ModuleConstans.MODULE_MAIN_NAME+":startMainActivity",null,null,new Object[]{getActivity()});
            } catch (Exception e) {
                e.printStackTrace();
            }
            getActivity().finish();
        }
    }*/

    /*@Override
    public void onFailed(Throwable e) {
        super.onFailed(e);
        showMessage(e.getMessage());
    }

    @Override
    public void onFinished() {
        super.onFinished();
        ViewUtils.dismissProgressDialog();
    }

    @Override
    public void onPrepare() {
        super.onPrepare();
        showLoadingDialog();
    }*/
}
