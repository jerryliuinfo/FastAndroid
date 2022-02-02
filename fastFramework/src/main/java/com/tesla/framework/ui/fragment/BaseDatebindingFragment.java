package com.tesla.framework.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.tesla.framework.R;
import com.tesla.framework.common.util.DebugUtils;
import com.tesla.framework.support.bean.DataBindingConfig;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by Jerry on 2021/5/24.
 */
public abstract class BaseDatebindingFragment<VB extends ViewDataBinding> extends BaseLifecycleFragment<VB> {
    protected VB mBinding;
    private TextView mTvStrictModeTip;



    protected abstract  DataBindingConfig getDataBindingConfig();



    /**
     * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
     * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
     * <p>
     * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
     *
     * @return binding
     */
    protected VB getBinding() {
        if (DebugUtils.isDebug() && mBinding != null) {
            if (mTvStrictModeTip == null) {
                mTvStrictModeTip = new TextView(getContext());
                mTvStrictModeTip.setAlpha(0.5f);
                mTvStrictModeTip.setPadding(
                        mTvStrictModeTip.getPaddingLeft() + 24,
                        mTvStrictModeTip.getPaddingTop() + 64,
                        mTvStrictModeTip.getPaddingRight() + 24,
                        mTvStrictModeTip.getPaddingBottom() + 24);
                mTvStrictModeTip.setGravity(Gravity.CENTER_HORIZONTAL);
                mTvStrictModeTip.setTextSize(10);
                mTvStrictModeTip.setBackgroundColor(Color.WHITE);
                String tip = String.format("%s 未遵循 DataBinding 严格模式，存在 Null 安全风险", getClass().getSimpleName());
                mTvStrictModeTip.setText(tip);
                mTvStrictModeTip.setTextColor(ColorUtils.getColor(R.color.comm_red));
                ((ViewGroup) mBinding.getRoot()).addView(mTvStrictModeTip);
            }
        }
        return mBinding;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DataBindingConfig dataBindingConfig = getDataBindingConfig();


        View contentView = inflater.inflate(dataBindingConfig.getLayout(), container, false);

        mBinding = DataBindingUtil.bind(contentView);
        //要使用LiveData对象作为数据绑定来源，需要设置LifecycleOwner, 这样当livedata数据变化后，xml就能察觉到
        mBinding.setLifecycleOwner(this);
        if (dataBindingConfig.getVmVariableId() != 0){
            mBinding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
        }
        SparseArray bindingParams = dataBindingConfig.getBindingParams();
        for (int i = 0, length = bindingParams.size(); i < length; i++) {
            mBinding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
        }

        return mBinding.getRoot();
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mBinding.unbind();
        mBinding = null;
    }

    @Override
    public int getLayoutId() {
        throw new IllegalArgumentException("don't call this method in BaseDatebindingFragment");
    }

}
