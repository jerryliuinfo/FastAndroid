package com.apache.fastandroid.demo.blacktech.viewpump

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.BlacktechViewPumpBinding
import com.apache.fastandroid.demo.blacktech.ViewPumpDemoActivity
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/10/18.
 * https://github.com/B3nedikt/ViewPump
 */
class ViewPumpDemoFragment: BaseBindingFragment<BlacktechViewPumpBinding>(BlacktechViewPumpBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        startActivity(Intent(context,ViewPumpDemoActivity::class.java))
    }

}