package com.apache.fastandroid.demo.drakeet

import android.os.Bundle
import com.apache.fastandroid.databinding.DrakeetLooperActivityBinding
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2021/10/18.
 */
class UnDeclareActivity: BaseVBActivity<DrakeetLooperActivityBinding>(DrakeetLooperActivityBinding::inflate) {


    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

    }

}