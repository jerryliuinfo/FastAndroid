package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_singleton.tv_result
import java.math.BigDecimal

/**
 * Created by Jerry on 2020/12/31.
 */
class SingleTonLiveData3Fragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_singleton3
    }



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        var priceLiveData = StockLiveData.get("01800")
        priceLiveData.observe(this, Observer<BigDecimal> {
            t -> tv_result.text = t.toString() })
        }
}