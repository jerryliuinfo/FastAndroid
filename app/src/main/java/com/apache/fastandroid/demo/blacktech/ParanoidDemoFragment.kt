package com.apache.fastandroid.demo.blacktech

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.BlackTechParanoidBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/1/19.
 */
class ParanoidDemoFragment: BaseBindingFragment<BlackTechParanoidBinding>(BlackTechParanoidBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


    }
}