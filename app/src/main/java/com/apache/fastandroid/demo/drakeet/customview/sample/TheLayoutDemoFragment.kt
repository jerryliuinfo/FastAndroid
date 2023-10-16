package com.apache.fastandroid.demo.drakeet.customview.sample

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.CustomViewgroupTheLayoutBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/10/15.
 */
class TheLayoutDemoFragment:BaseBindingFragment<CustomViewgroupTheLayoutBinding>(CustomViewgroupTheLayoutBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)



    }
}