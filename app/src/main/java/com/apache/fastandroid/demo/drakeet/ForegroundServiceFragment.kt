package com.apache.fastandroid.demo.drakeet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class ForegroundServiceFragment:BaseFragment() {
    private lateinit var msg:String
    override fun inflateContentView(): Int {
        return R.layout.fragment_common
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }


}