package com.tesla.framework.component.livedata

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.tesla.framework.applike.FApplication
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2021/4/23.
 */
class NetworkLiveData:LiveData<Int>() {

    private var networkCallback: ConnectivityManager.NetworkCallback

    private val mListeners = ArrayList<NetworkListener>();

    private var request: NetworkRequest

    private var manager: ConnectivityManager

    init {
        networkCallback = NetworkCallbackImpl()
        request = NetworkRequest.Builder().build()
        manager = FApplication.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onActive() {
        super.onActive()
        manager.registerNetworkCallback(request, networkCallback)
    }

    override fun onInactive() {
        super.onInactive()

        manager.unregisterNetworkCallback(networkCallback)
    }

    object NetworkLiveDataHolder {
        val INSTANCE = NetworkLiveData()
    }


    fun register(owner: LifecycleOwner, listener: NetworkListener){
        if (!mListeners.contains(listener)){
            mListeners.add(listener);
        }

    }

    companion object {
        private const val TAG = "NetworkLiveData";
        fun getInstance(): NetworkLiveData {
            return NetworkLiveDataHolder.INSTANCE
        }
    }

    class NetworkCallbackImpl : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Logger.d("onAvailable: ", "网络已连接")
            getInstance().postValue(NetworkState.CONNECT)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Logger.d("onLost 网络断开")
            getInstance().postValue(NetworkState.NONE)
        }

        override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Logger.d("WIFI已连接")
                getInstance().postValue(NetworkState.WIFI)
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Logger.d("移动网络已连接")
                getInstance().postValue(NetworkState.CELLULAR)
            }
        }
    }


    interface NetworkListener{

    }



    object NetworkState{
        // 无网络
        const val NONE = 0

        // 网络连接
        const val CONNECT = 1

        // WIFI
        const val WIFI = 2

        // 移动网络
        const val CELLULAR = 3
    }
}