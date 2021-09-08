package com.tesla.framework.ui.fragment;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tesla.framework.support.bean.DataBindingConfig;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by Jerry on 2021/5/24.
 */
public abstract class BaseDatebindingFragment<VB extends ViewDataBinding> extends BaseLifecycleFragment<VB> {
    protected VB mBinding;



    protected abstract DataBindingConfig getDataBindingConfig();

    protected abstract void initViewModel();


    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
     * <p>
     * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
     *
     * @return binding
     */
    protected VB getBinding() {

        return mBinding;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DataBindingConfig dataBindingConfig = getDataBindingConfig();


        View contentView = inflater.inflate(dataBindingConfig.getLayout(), container, false);

        mBinding = DataBindingUtil.bind(contentView);
        mBinding.setLifecycleOwner(this);
        if (dataBindingConfig.getVmVariableId() != 0){
            mBinding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        }
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            mBinding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }
        setContentView((ViewGroup) mBinding.getRoot());


        return mBinding.getRoot();
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mBinding.unbind();
        mBinding = null;
    }

    @Override
    public int inflateContentView() {
        throw new IllegalArgumentException("don't call this method in BaseDatebindingFragment");
    }
}
