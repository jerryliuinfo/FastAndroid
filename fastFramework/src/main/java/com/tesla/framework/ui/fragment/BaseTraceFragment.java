package com.tesla.framework.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseTraceFragment<Q extends ViewDataBinding> extends Fragment implements IView {
    private static final String TAG = "BaseTraceFragment";
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
        if (rootView == null && getLayoutId() > 0) {
            viewDataBinding = DataBindingUtil.inflate(
                    inflater, getLayoutId(), container, false);
            rootView = viewDataBinding.getRoot();
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
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData(savedInstanceState);
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



}
