package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.component.livedata.LiveDataBus
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_bus.*
import kotlin.random.Random

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataBusFragment :BaseFragment(){
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_bus
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        LiveDataBus.get().with("banner").observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                FastLog.d(TAG, "onChanged t: %s", t)
                tv_result.text = "接收: $t"
            }
        })


        btn_send.setOnClickListener {
            LiveDataBus.get().with("banner").postValue("Hello : ${Random.nextInt(100)}" )
        }
    }

}