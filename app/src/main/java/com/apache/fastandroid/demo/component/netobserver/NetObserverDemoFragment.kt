package com.apache.fastandroid.demo.component.netobserver

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentNetObserverBinding
import com.tesla.framework.component.netobserver.NetworkObserver
import com.tesla.framework.component.netobserver.NetworkStateObserver
import com.tesla.framework.component.netobserver.Reachability
import com.tesla.framework.kt.runOnUiThread
import com.tesla.framework.kt.showToast
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Created by Jerry on 2023/9/26.
 */
class NetObserverDemoFragment :
    BaseBindingFragment<FragmentNetObserverBinding>(FragmentNetObserverBinding::inflate) {


    companion object{
        private const val SERVER_URL = "https://www.baidu.com"
    }




    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnLivedata.setOnClickListener {
            observerWithLiveData()
        }

        mBinding.btnFlow.setOnClickListener {
            observerWithFlow()
        }
    }

    private fun observerWithFlow() {
        val network = NetworkStateObserver.Builder()
            .activity(requireActivity())
            .build()
        val activity = requireActivity()

        lifecycleScope.launch {
            val observe: Flow<NetworkObserver.Status> = network.callNetworkConnectionFlow().observe()
            observe.collect {
                when (it) {
                    NetworkObserver.Status.Available -> {
                        lifecycleScope.launch {
                            when {
                                Reachability.hasServerConnectedFlow(
                                    context = activity,
                                    serverUrl = "https://www.github.com"
                                ).retryWhen { cause, attempt ->
                                    if (cause is IOException && attempt < 3) {
                                        delay(2000)
                                        return@retryWhen true
                                    } else {
                                        return@retryWhen false
                                    }
                                }.buffer().first() -> lifecycleScope.launch {
                                    showToast(
                                        "Server url works"
                                    )
                                }

                                Reachability.hasInternetConnectedFlow(
                                    context = activity
                                ).retryWhen { cause, attempt ->
                                    if (cause is IOException && attempt < 3) {
                                        delay(2000)
                                        return@retryWhen true
                                    } else {
                                        return@retryWhen false
                                    }
                                }.buffer().first() -> lifecycleScope.launch {
                                    showToast(
                                        "Network restored"
                                    )
                                }

                                else -> lifecycleScope.launch {
                                    showToast(
                                        "Network is lost or issues with server"
                                    )
                                }
                            }
                        }
                    }

                    NetworkObserver.Status.Unavailable -> {
                        showToast(
                            "Network is unavailable!"
                        )
                    }

                    NetworkObserver.Status.Losing -> {
                        showToast(
                            "You are losing your network!"
                        )
                    }

                    NetworkObserver.Status.Lost -> {
                        showToast(
                            "Network is lost!"
                        )
                    }
                }
            }

        }
    }



    private fun observerWithLiveData() {
        val network = NetworkStateObserver.Builder()
            .activity(requireActivity())
            .build()
        val activity = requireActivity()
        network.callNetworkConnection().observe(this) { isConnected ->
            lifecycleScope.launch(Dispatchers.IO) {
                if (isConnected) {
                    when {
                        Reachability.hasServerConnected(
                            context = activity,
                            serverUrl = SERVER_URL
                        ) -> lifecycleScope.launchWhenStarted {
                            updateNetworkText("Server url works")

                        }

                        Reachability.hasInternetConnected(
                            context = activity
                        ) -> lifecycleScope.launchWhenStarted {
                            updateNetworkText("Network restored")
                        }

                        else -> lifecycleScope.launchWhenStarted {

                            updateNetworkText("Network is lost or issues with server")

                        }
                    }
                } else {
                    //check for lost connection
                    lifecycleScope.launchWhenStarted {
                        updateNetworkText("No Network connection")
                    }
                }

            }
        }
    }

    private fun updateNetworkText(msg:String){
        runOnUiThread {
            mBinding.networkStatus.text = msg
        }
        showToast(
            "No Network connection"
        )
    }
}