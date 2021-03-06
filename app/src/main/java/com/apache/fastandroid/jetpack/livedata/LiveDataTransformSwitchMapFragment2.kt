package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_transform_switch_map2.*

/**
 * Created by Jerry on 2021/2/7.
 */
class LiveDataTransformSwitchMapFragment2: BaseFragment() {
    companion object{
        private const val TAG = "LiveDataTransformMapFragment"
    }

    private val userViewModel by lazy {
        UserInfoViewModel(UserReporsity.get())
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_transform_switch_map2
    }

    private var mSwitch:Boolean = true

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        userViewModel.livedata1.value = "livedata1"
        userViewModel.livedata2.value = "livedata2"
        btn_switch.setOnClickListener {
            mSwitch = !mSwitch
            userViewModel.livedataSwitch.value = mSwitch
        }
        userViewModel.livedataSwitchMap.observe(this, Observer {
            text_name.text = it
        })


    }
}