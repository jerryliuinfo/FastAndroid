package com.apache.fastandroid.jetpack.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ProcessLifecycleOwner
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleForegroundBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class AppForeGroundFragment :
    BaseBindingFragment<FragmentJetpackLifecycleForegroundBinding>(FragmentJetpackLifecycleForegroundBinding::inflate),
    TraditionalProcessLifecycleListener.LifecycleCallbackListener {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        mBinding.btnProcessLifeCycleOwnerForeground.setOnClickListener {
            ProcessLifecycleOwner.get().lifecycle.addObserver(LifecyclerChecker())
        }


    }

    override fun onAppForeground() {
        Logger.d("onAppForeground -->")
        toast("onAppForeground -->")
    }

    override fun onAppBackground() {
        Logger.d("onAppBackground -->")
        toast("onAppBackground -->")
    }


}