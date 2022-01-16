package com.apache.fastandroid.demo.bestpay

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.WxPayResp
import com.apache.fastandroid.demo.bestpay.wxpay.WxPay
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 * Created by Jerry on 2022/1/15.
 */
class BestPayDemoFragment:BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_bestpay
    }

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
