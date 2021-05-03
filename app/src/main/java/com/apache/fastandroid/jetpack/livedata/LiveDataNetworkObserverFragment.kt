package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.livedata.LiveDataBus
import com.tesla.framework.component.livedata.NetworkLiveData
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_bus.*
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_network_observer.*
import kotlin.random.Random

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataNetworkObserverFragment :BaseFragment(){
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_network_observer
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        NetworkLiveData.getInstance().observe(this, Observer<Int> {
            NLog.d(TAG , "network:${it}")
            tv1.text = "$it"
        })
    }

}