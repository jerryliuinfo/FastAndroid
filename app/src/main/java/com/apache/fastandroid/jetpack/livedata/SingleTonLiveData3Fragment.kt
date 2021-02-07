package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.component.livedata.SingleTonViewModel
import com.tesla.framework.ui.activity.FragmentContainerActivity
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_singleton.*
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_singleton.tv_result
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_singleton3.*
import java.math.BigDecimal

/**
 * Created by Jerry on 2020/12/31.
 */
class SingleTonLiveData3Fragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_singleton3
    }



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        val priceLiveData = StockLiveData("01800")
        priceLiveData.observe(this, Observer<BigDecimal> {
            t -> tv_result.text = t.toString() })
        }
}