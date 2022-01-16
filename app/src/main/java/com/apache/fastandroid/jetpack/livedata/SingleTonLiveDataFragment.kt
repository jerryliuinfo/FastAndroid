package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.component.livedata.SingleTonViewModel
import com.tesla.framework.ui.activity.FragmentContainerActivity
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_singleton.*

/**
 * Created by Jerry on 2020/12/31.
 */
class SingleTonLiveDataFragment: BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_jetpack_livedata_singleton
    }


    private val viewModel: SingleTonViewModel by lazy {
        SingleTonViewModel()
    }
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        viewModel.singleTonLiveData.observe(this
        ) { t -> tv_result.text = t }

        btn_jump.setOnClickListener {
            FragmentContainerActivity.launch(activity,SingleTonLiveDataFragment2::class.java,null)
        }
    }
}