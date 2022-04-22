package com.apache.fastandroid.demo.sample

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.Sample1CheckCallbackExistBinding
import com.apache.fastandroid.databinding.SampleCode2Binding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.sample1_check_callback_exist.*

/**
 * Created by  on 2021/12/18.
 */
class SampleCodeDemo2Fragment:BaseVBFragment<SampleCode2Binding>(SampleCode2Binding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }


}