package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.TempGenericBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/8/13.
 */
class GenericClassDemoFragment: BaseVBFragment<TempGenericBinding>(TempGenericBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }
}