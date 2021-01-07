package com.apache.fastandroid.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_jetpack_viewmodel.*
import kotlin.random.Random

/**
 * Created by Jerry on 2020/11/1.
 */
class ViewModelFragment:ABaseFragment() {
    private val userInfoViewModel :UserInfoViewModel by lazy {
        UserInfoViewModel()
    }

    override fun inflateContentView(): Int {
       return R.layout.fragment_jetpack_viewmodel
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        tv_msg.text = userInfoViewModel.loadUser().value
        FastLog.d(TAG,"userInfoViewModel.name:${userInfoViewModel.loadUser().value}")
        userInfoViewModel.loadUser().observe(this, Observer<String> {
            FastLog.d(TAG, "UserInfoViewModel onChange: $it")
            tv_msg.text = it
        })
        btn_change.setOnClickListener {
            userInfoViewModel.loadUser().postValue("Python:${Random.nextInt(10)}")
        }
    }
}