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
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseTraceFragment<Q extends ViewDataBinding> extends Fragment implements IView {
    public static final String TAG = "BaseTraceFragment";
    private View rootView;
    protected LayoutInflater layoutInflater;
    //不要用这个mViewModel， 这是个空对象
    protected Q viewDataBinding;

    protected AppCompatActivity mActivity;

    private boolean isFirstLoad = true;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        layoutInflater = inflater;
        if (rootView == null && inflateContentView() > 0) {
            viewDataBinding = DataBindingUtil.inflate(
                    inflater, inflateContentView(), container, false);
            setContentView((ViewGroup) viewDataBinding.getRoot());
            bindUI(rootView);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        return rootView;
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
}
