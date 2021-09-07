package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.livedata.NetworkLiveData
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_network_observer.*

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataNetworkObserverFragment : BaseStatusFragmentNew(){
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