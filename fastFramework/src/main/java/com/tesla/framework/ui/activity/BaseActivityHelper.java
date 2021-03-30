package com.tesla.framework.ui.activity;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import com.tesla.framework.component.lifecycle.FullLifecycleObserver;
import com.tesla.framework.component.lifecycle.FullLifecycleObserverAdapter;

import org.jetbrains.annotations.NotNull;

import androidx.lifecycle.LifecycleOwner;

/**
 * 用户注册回调BaseActivity的生命周期及相关的方法，自行添加
 * 1.可以在Activity生命周期方法内做一些业务处理
 * 2.按home键，返回键,toolbar向左返回箭头处理
 * 3.主题配置
 *
 *
 * Created by JerryLiu on 17/04/08.
 */
public class BaseActivityHelper implements FullLifecycleObserver {
    //当前Activity
    private BaseActivity mActivity;

    /**
     * 判断当前Activity是否在前台。
     */
    protected boolean isActive;

    /**
     * 当前Activity的实例。
     */
    protected Activity activity;

    private LifecycleOwner mLifecycleOwner;

    public BaseActivityHelper(BaseActivity mActivity, LifecycleOwner mLifecycleOwner) {
        this.mActivity = mActivity;
        this.mLifecycleOwner = mLifecycleOwner;
        mLifecycleOwner.getLifecycle().addObserver(new FullLifecycleObserverAdapter(mLifecycleOwner,this));
    }

    public BaseActivity getActivity() {
        return mActivity;
    }

    public View findViewById(int id) {
        return mActivity.findViewById(id);
    }


    protected boolean onHomeClick() {
        return false;
    }

    public boolean onBackClick() {
        return false;
    }

    protected int configTheme() {
        return 0;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onStart(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onResume(@NotNull LifecycleOwner owner) {
        isActive = true;
    }

    @Override
    public void onPause(@NotNull LifecycleOwner owner) {
        isActive = false;
    }

    @Override
    public void onStop(@NotNull LifecycleOwner owner) {

    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        mActivity = null;
    }
}
