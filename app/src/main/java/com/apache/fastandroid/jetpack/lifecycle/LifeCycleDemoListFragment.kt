package com.apache.fastandroid.jetpack.lifecycle

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentLifecycleModeListBinding
import com.apache.fastandroid.jetpack.lifecycle.customlifecycle.CustomLifecycleOwnerFragment
import com.apache.fastandroid.jetpack.lifecycle.location.ILocationListener
import com.apache.fastandroid.jetpack.lifecycle.location.LocationManager
import com.apache.fastandroid.jetpack.lifecycle.service.MyLifeCycleService
import com.apache.fastandroid.jetpack.lifecycle.traditional.GpsEngine
import com.apache.fastandroid.jetpack.lifecycle.traditional.TraditionalLifeCycleFragment
import com.tesla.framework.component.lifecycle.CustomIntentProvider
import com.tesla.framework.component.lifecycle.LifeCycleBroadcastReceiver
import com.tesla.framework.component.lifecycle.LifecycleHandler
import com.tesla.framework.component.lifecycle.maybeObserveLifecycle2
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.launchFragment
import com.tesla.framework.kt.lifeCycleOwner
import com.tesla.framework.kt.showToast
import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2020/10/31.
 */
class LifeCycleDemoListFragment :
    BaseBindingFragment<FragmentLifecycleModeListBinding>(FragmentLifecycleModeListBinding::inflate) {


    private var lifecycleHandler: LifecycleHandler? = null

    private val mLocationManager: LocationManager by lazy {
        LocationManager(this@LifeCycleDemoListFragment)
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)



        mBinding.btnTraditionalLifecycle.setOnClickListener {
            // 在 onResume 和 onPause中手动调用

            activity?.launchFragment<TraditionalLifeCycleFragment>()
        }

        mBinding.btnLifecycleLocation.setOnClickListener {
            lifeCycleLocation()
        }

        mBinding.btnLifecycleService.setOnClickListener {
            lifeCycleServiceUsage()
        }

        mBinding.btnMaybeObserveLifecycle.setOnClickListener {
            maybeObserveLifeCycle()
        }

        mBinding.btnDialogDismiss.setOnClickListener {
            dialogAutoDismiss()
        }

        mBinding.btnLifecycleHandler.setOnClickListener {
            lifecycleHandlerTest()
        }

        mBinding.btnBroadcastReceiver.setOnClickListener {
            broadcastReceiverUsage()
        }

        mBinding.btnCustomLifecycle.setOnClickListener {
            customLifeCycleOwner()
        }
    }

    private fun lifeCycleServiceUsage() {

        lifecycleScope.launch {
            val serviceIntent = Intent(requireContext(), MyLifeCycleService::class.java)
            // 启动服务
            requireContext().startService(serviceIntent)

            // delay
            delay(5000)
            // 停止服务
            requireContext().stopService(serviceIntent)
        }
    }

    private fun customLifeCycleOwner() {
        FragmentContainerActivity.launch(
            activity = requireActivity(),
            clazz = CustomLifecycleOwnerFragment::class.java
        )
    }

    private fun lifecycleHandlerTest() {
        lifecycleHandler = LifecycleHandler(this).apply {
            postDelayed({
                Log.d("tag", "10s 后我将会执行")
            }, 10000)
        }
    }

    private fun dialogAutoDismiss() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("我是标题")
            .setPositiveButton("确认") { _, _ ->
                println("onclick confirm button")
            }
            .show()
        dialog.setOnDismissListener {
            println("dialog onDismiss --->")

        }
        dialog.apply {
            lifeCycleOwner(this@LifeCycleDemoListFragment)
        }

    }

    private fun maybeObserveLifeCycle() {
        this.maybeObserveLifecycle2(Lifecycle.Event.ON_RESUME, Lifecycle.Event.ON_DESTROY) {
            showToast("maybeObserveLifecycle onDestroy event:${it}")
        }

    }

    private fun lifeCycleLocation() {
        mLocationManager.addListener(object : ILocationListener {
            override fun onLocationChanged(location: Long) {
                Logger.d("${LocationManager.TAG} onLocationChanged latitude:${location} ")

            }
        })

    }

    override fun onResume() {
        super.onResume()
        GpsEngine.getInstance().onResumeAction()
    }

    override fun onPause() {
        super.onPause()
        GpsEngine.getInstance().onPauseAction()
    }

    private fun broadcastReceiverUsage() {
        val lifecycleReceiver = LifeCycleBroadcastReceiver(requireContext(), CustomIntentProvider()) {
            Logger.d("onReceiver ")
        }
    }


}