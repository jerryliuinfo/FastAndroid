package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.tesla.framework.component.livedata.SingleTonViewModel
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_singleton2.*
import java.util.*

/**
 * Created by Jerry on 2020/12/31.
 */
class SingleTonLiveDataFragment2: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_singleton2
    }

    private val viewModel:SingleTonViewModel by lazy {
        SingleTonViewModel()
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        btn_send_data.setOnClickListener {
            viewModel.singleTonLiveData.value = "我是页面2修改的数据:${Random().nextInt(10)}"
        }
    }
}