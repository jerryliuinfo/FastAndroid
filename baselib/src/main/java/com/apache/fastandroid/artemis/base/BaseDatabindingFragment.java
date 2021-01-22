package com.apache.fastandroid.artemis.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesla.framework.ui.fragment.ABaseFragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;


/**
 * Created by 01370340 on 2017/9/1.
 */

public abstract class BaseDatabindingFragment<B extends ViewDataBinding> extends ABaseFragment {

    protected B binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getContentView() == null && inflateContentView() > 0){
            binding = DataBindingUtil.setContentView(getActivity(), inflateContentView());
            setContentView((ViewGroup) binding.getRoot());
        }else {
            ViewGroup viewGroup = (ViewGroup) getContentView().getParent();
            if (viewGroup != null) {
                viewGroup.removeView(getContentView());
            }
        }
        return getContentView();
    }


}
