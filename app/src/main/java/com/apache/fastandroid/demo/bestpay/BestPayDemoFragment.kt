package com.apache.fastandroid.demo.bestpay

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentBestpayBinding
import com.apache.fastandroid.demo.bean.WxPayResp
import com.apache.fastandroid.demo.bestpay.wxpay.WxPay
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/1/15.
 */
class BestPayDemoFragment:BaseBindingFragment<FragmentBestpayBinding>(FragmentBestpayBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        WxPay.getInstance().getWxAPi()?.handleIntent()
    }

    fun onResp(wxPayResp: WxPayResp){
        if (wxPayResp.type == 100){
            WxPay.getInstance().onResp(wxPayResp.code,wxPayResp.message)
            requireActivity().finish()
        }
    }
}
