package com.apache.fastandroid.demo.weaknetwork

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.blankj.utilcode.util.Utils


/**
 * Created by Jerry on 2023/3/31.
 */
public class ConnectiveManagerTest : INetworkTest {
    override fun testNetwork(): Boolean {
        val connMgr =
            Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network: Network? = connMgr.activeNetwork
        val networkCapabilities = connMgr.getNetworkCapabilities(network)
        val isWeakNet =
            (!networkCapabilities!!.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
                    && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))

        return !isWeakNet

    }
}