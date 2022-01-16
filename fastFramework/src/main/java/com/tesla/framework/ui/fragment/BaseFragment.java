package com.tesla.framework.ui.fragment;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.ui.activity.BaseActivity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

/**
 * Created by 01370340 on 2017/9/1.
 */

public abstract class BaseFragment extends Fragment implements IView{
    public static final String TAG = "BaseFragment";
    private View rootView;
    protected AppCompatActivity mActivity;
    private boolean isFirstLoad = true;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutId() > 0) {
            ViewGroup contentView = (ViewGroup) inflater.inflate(getLayoutId(), null);
            contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            this.rootView = contentView;
            bindUI(rootView);

            return rootView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layoutInit(null,savedInstanceState);
    }


    public View getRootView(){
        return rootView;
    }

    public <T extends View> T findViewById(@IdRes int resId){
        return getRootView().findViewById(resId);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad){
            isFirstLoad = false;
            onLazyLoad();
        }
    }

    /**
     * Fragment 懒加载
     */
    protected void onLazyLoad(){

    }




    protected void setToolbarTitle(String msg){
        BaseActivity baseActivity = (BaseActivity) getActivity();
        ActionBar supportActionBar = baseActivity.getSupportActionBar();
        FastLog.d(TAG, "supportActionBar: %s",supportActionBar);
        if (supportActionBar != null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(msg);
        }
    }

    @Override
    public void bindUI(View rootView) {

    }

    @Override
    public void layoutInit(LayoutInflater inflater, Bundle savedInstanceState) {

    }

    public void setContentView(ViewGroup view) {
        this.rootView = view;
    }

    /**
     * Action的home被点击了
     *
     * @return
     */
    public boolean onHomeClick() {
        return onBackClick();
    }

    /**
     * 返回按键被点击了
     *
     * @return
     */
    public boolean onBackClick() {
        return false;
    }



    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;


    public <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) {
            mFragmentProvider = new ViewModelProvider(this);
        }
        return mFragmentProvider.get(modelClass);
    }

    public <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(mActivity);
        }
        return mActivityProvider.get(modelClass);
    }

    /**
     * 全局共享的ViewModel,类似于单例，但是跟单例不同的是，这个ViewModel仅限于在Activity或者Fragment当中使用
     * @param modelClass
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider(
                    (ViewModelStoreOwner) mActivity.getApplicationContext(), getApplicationFactory(mActivity));
        }
        return mApplicationProvider.get(modelClass);
    }

    private ViewModelProvider.Factory getApplicationFactory(Activity activity) {
        checkActivity(this);
        Application application = checkApplication(activity);
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application);
    }

    private void checkActivity(Fragment fragment) {
        Activity activity = fragment.getActivity();
        if (activity == null) {
            throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
        }
    }


    private Application checkApplication(Activity activity) {
        Application application = activity.getApplication();
        if (application == null) {
            throw new IllegalStateException("Your activity/fragment is not yet attached to "
                    + "Application. You can't request ViewModel before onCreate call.");
        }
        return application;
    }

    protected  void initViewModel(){};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }




}
