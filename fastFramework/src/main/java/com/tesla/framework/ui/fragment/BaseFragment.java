package com.tesla.framework.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.ui.activity.BaseActivity;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by 01370340 on 2017/9/1.
 */

public abstract class BaseFragment extends Fragment implements IView{
    public static final String TAG = "BaseFragment";
    private View rootView;
    protected LayoutInflater layoutInflater;


    protected AppCompatActivity mActivity;

    private boolean isFirstLoad = true;

    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layoutInflater = inflater;
        if (inflateContentView() > 0) {
            ViewGroup contentView = (ViewGroup) inflater.inflate(inflateContentView(), null);
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


    @Override
    public void bindUI(View rootView) {

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








}
