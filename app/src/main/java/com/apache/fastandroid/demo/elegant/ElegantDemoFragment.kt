package com.apache.fastandroid.demo.elegant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import com.apache.fastandroid.databinding.FragmentElegantDemoBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/3/11.
 */
class ElegantDemoFragment:BaseBindingFragment<FragmentElegantDemoBinding>(FragmentElegantDemoBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnAddListener.setOnClickListener {
            AndroidClient.addClientChangeListener(object :AndroidClient.IClientChangeListener{
                override fun onClientChanged(result: String) {
                    Logger.d("client changed: $result")
                    AndroidClient.removeClientChangeListener(this)
                }
            })

            it.viewTreeObserver.addOnPreDrawListener (object : ViewTreeObserver.OnPreDrawListener{
                override fun onPreDraw(): Boolean {
                    it.viewTreeObserver.removeOnPreDrawListener(this)
                    return false
                }
            })

        }

        mBinding.btnTrigger.setOnClickListener {
            AndroidClient.trigger()
        }

    }
}