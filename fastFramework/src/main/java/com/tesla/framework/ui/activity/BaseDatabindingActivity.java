package com.tesla.framework.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tesla.framework.common.util.DebugUtils;
import com.tesla.framework.support.bean.DataBindingConfig;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by Jerry on 2021/9/23.
 */
public abstract class BaseDatabindingActivity<VB extends ViewDataBinding> extends BaseActivity{
   protected VB mBinding;
   private TextView mTvStrictModeTip;

   protected abstract void initViewModel();

   protected abstract DataBindingConfig getDataBindingConfig();

   /**
    * TODO tip: 警惕使用。非必要情况下，尽可能不在子类中拿到 binding 实例乃至获取 view 实例。使用即埋下隐患。
    * 目前的方案是在 debug 模式下，对获取实例的情况给予提示。
    * <p>
    * 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
    *
    * @return binding
    */
   protected ViewDataBinding getBinding() {
      if (DebugUtils.isDebug() && mBinding != null) {
         if (mTvStrictModeTip == null) {
            mTvStrictModeTip = new TextView(getApplicationContext());
            mTvStrictModeTip.setAlpha(0.4f);
            mTvStrictModeTip.setPadding(
                    mTvStrictModeTip.getPaddingLeft() + 24,
                    mTvStrictModeTip.getPaddingTop() + 64,
                    mTvStrictModeTip.getPaddingRight() + 24,
                    mTvStrictModeTip.getPaddingBottom() + 24);
            mTvStrictModeTip.setGravity(Gravity.CENTER_HORIZONTAL);
            mTvStrictModeTip.setTextSize(10);
            mTvStrictModeTip.setBackgroundColor(Color.WHITE);
            @SuppressLint({"StringFormatInvalid", "LocalSuppress"})
            String tip = String.format("%s 未遵循 DataBinding 严格模式，存在 Null 安全风险", getClass().getSimpleName());
            mTvStrictModeTip.setText(tip);
            ((ViewGroup) mBinding.getRoot()).addView(mTvStrictModeTip);
         }
      }
      return mBinding;
   }



   @Override
   public void layoutInit(Bundle savedInstanceState) {
      super.layoutInit(savedInstanceState);

      initViewModel();
      DataBindingConfig dataBindingConfig = getDataBindingConfig();

      //TODO tip: DataBinding 严格模式：
      // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
      // 通过这样的方式，来彻底解决 视图调用的一致性问题，
      // 如此，视图调用的安全性将和基于函数式编程思想的 Jetpack Compose 持平。

      // 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910

      mBinding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
      mBinding.setLifecycleOwner(this);
      mBinding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
      SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
      for (int i = 0, length = bindingParams.size(); i < length; i++) {
         mBinding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
      }
   }

   @Override
   protected void onDestroy() {
      super.onDestroy();
      mBinding.unbind();
      mBinding = null;
   }


   /**
    * 使用databinding 不通过这个方法来设置视图
    * @return
    */
   @Override
   public int inflateContentView() {
      return -1;
   }
}
