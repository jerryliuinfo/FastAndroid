package com.tesla.framework.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class ABaseDatabindingFragment<B extends ViewDataBinding> extends  BaseFragment{

    protected B binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getRootView() == null && inflateContentView() > 0){
            View contentView = inflater.inflate(inflateContentView(), container, false);
            binding = DataBindingUtil.bind(contentView);
            setContentView((ViewGroup) binding.getRoot());
        }else {
            ViewGroup viewGroup = (ViewGroup) getRootView().getParent();
            if (viewGroup != null) {
                viewGroup.removeView(getRootView());
            }
        }
        return getRootView();
    }


}
