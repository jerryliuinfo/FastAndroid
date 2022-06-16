package com.apache.fastandroid.jetpack.lifecycle

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ProcessLifecycleOwner
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleBinding
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleForegroundBinding
import com.apache.fastandroid.jetpack.lifecycle.LocationListener.OnLocationChangeListener
import com.blankj.utilcode.util.Utils
import com.example.android.architecture.blueprints.todoapp.util.showDialog
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.lifeCycleOwner
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class AppForeGroundFragment :
    BaseVBFragment<FragmentJetpackLifecycleForegroundBinding>(FragmentJetpackLifecycleForegroundBinding::inflate),
    TraditionalProcessLifecycleListener.LifecycleCallbackListener {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        mBinding.btnProcessLifeCycleOwnerForeground.setOnClickListener {
            ProcessLifecycleOwner.get().lifecycle.addObserver(LifecyclerChecker())
        }


    }

    override fun onAppForeground() {
        Logger.d("onAppForeground -->")
        showToast("onAppForeground -->")
    }

    override fun onAppBackground() {
        Logger.d("onAppBackground -->")
        showToast("onAppBackground -->")
    }


}