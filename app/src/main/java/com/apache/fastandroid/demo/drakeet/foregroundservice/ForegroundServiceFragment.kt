package com.apache.fastandroid.demo.drakeet.foregroundservice

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class ForegroundServiceFragment:BaseFragment() {
    private lateinit var msg:String

    private val handler = Handler()
    override fun getLayoutId(): Int {
        return R.layout.fragment_common
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        startActivity(Intent(activity,ForegroundServiceActivity::class.java))

    }




}