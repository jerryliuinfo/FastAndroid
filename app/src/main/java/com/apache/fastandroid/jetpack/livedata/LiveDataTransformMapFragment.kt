package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.executor.Observer
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_transform_map.*

/**
 * Created by Jerry on 2021/2/7.
 */
class LiveDataTransformMapFragment:ABaseFragment() {
    companion object{
        private const val TAG = "LiveDataTransformMapFragment"
    }

    private val userViewModel by lazy {
        UserInfoViewModel()
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_transform_map
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        userViewModel.userName.observe(this, androidx.lifecycle.Observer {
            NLog.d(TAG, "userName onChange: %s", it)
            text_name.text = it
        })
        userViewModel.user.observe(this, androidx.lifecycle.Observer {
            NLog.d(TAG, "user onChange: %s", it)
            text_name.text = it.name
        })
        btn_change.setOnClickListener {
            userViewModel.changeValue()
        }
    }
}