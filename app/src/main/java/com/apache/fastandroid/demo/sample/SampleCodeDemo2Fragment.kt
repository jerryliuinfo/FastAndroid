package com.apache.fastandroid.demo.sample

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.SampleCode2Binding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by  on 2021/12/18.
 */
class SampleCodeDemo2Fragment:BaseBindingFragment<SampleCode2Binding>(SampleCode2Binding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }


}