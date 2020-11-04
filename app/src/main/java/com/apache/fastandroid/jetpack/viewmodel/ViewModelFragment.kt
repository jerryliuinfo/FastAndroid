package com.apache.fastandroid.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.apache.fastandroid.LogUtils
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_jetpack_viewmodel.*

/**
 * Created by Jerry on 2020/11/1.
 */
class ViewModelFragment:ABaseFragment() {
    private val userInfoViewModel by lazy {
        UserInfoViewModel()
    }
    override fun inflateContentView(): Int {
       return R.layout.fragment_jetpack_viewmodel
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

//        val tv_msg:TextView = findViewById(R.id.tv_msg) as TextView
        tv_msg.text = userInfoViewModel.name
        LogUtils.d("userInfoViewModel.name:${userInfoViewModel.name}")

        btn_change.setOnClickListener {
            userInfoViewModel.name = "Python"
        }
    }
}