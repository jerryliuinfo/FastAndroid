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

/**
 * Created by Jerry on 2020/12/31.
 */
class SingleTonLiveDataFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_singleton
    }


    private val viewModel: SingleTonViewModel by lazy {
        SingleTonViewModel()
    }
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        viewModel.singleTonLiveData.observe(this, Observer<String> {
            FastLog.d(TAG, "onChange: %s", it)
            tv_result.text = it
        })

        btn_jump.setOnClickListener {
            FragmentContainerActivity.launch(activity,SingleTonLiveDataFragment2::class.java,null)
        }
    }
}