package com.apache.fastandroid.demo.designmode.paramtype

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentDesignParamTypeBinding
import com.apache.fastandroid.demo.designmode.proxy.ServiceApiV2
import com.apache.fastandroid.demo.designmode.proxy.ServiceMgr
import com.apache.fastandroid.demo.designmode.proxy.WechatApi
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.fragment_design_param_type.*

/**
 * Created by Jerry on 2021/9/21.
 */
class ParamTypeDemoFragment:BaseVBFragment<FragmentDesignParamTypeBinding>(FragmentDesignParamTypeBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        btn_param_type.setOnClickListener {
            val serviceApiV2 = ServiceMgr.getInstance().getApi(
                ServiceApiV2::class.java
            )

            val wechatApi = ServiceMgr.getInstance().getApi(
                WechatApi::class.java
            )
        }

    }
}