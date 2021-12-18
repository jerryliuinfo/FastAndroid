package com.apache.fastandroid.demo.sample

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.sample1_check_callback_exist.*

/**
 * Created by  on 2021/12/18.
 */
class SampleCode1DemoFragment:BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.sample1_check_callback_exist
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        var owner = BuilderOwner.Builder()
            .addCallback(BuilderOwner.CallbackImpl1())
            .addCallback(BuilderOwner.CallbackImpl2())
            .addCallback(BuilderOwner.CallbackImpl3()).build()
        owner.register()
        btn_check_callback.setOnClickListener {
            owner.showCallback(BuilderOwner.CallbackImpl1::class.java)
        }
    }



}