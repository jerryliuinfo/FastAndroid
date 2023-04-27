package com.apache.fastandroid.demo.component.loadsir.sample

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.LoadsirLayoutPlaceholderBinding
import com.apache.fastandroid.demo.component.loadsir.sample.callback.PlaceholderCallback
import com.apache.fastandroid.demo.kt.extensions.loadSirOptions
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by  on 2021/12/18.
 */
class LoadSirPlaceholderActivity:BaseBindingFragment<LoadsirLayoutPlaceholderBinding>(LoadsirLayoutPlaceholderBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val loadSir:LoadSir = LoadSir.Builder().apply {
            addCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.PlaceholderCallback())
            setDefaultCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.PlaceholderCallback::class.java)
        }.build()
        //do retry logic...
        val loadService  = loadSir.register(activity) {

        }
        runOnUIDelay({
                  loadService.showSuccess()
        }, 1000)

        loadSirOptions {
            addCallback(object : com.kingja.loadsir.callback.Callback() {
                override fun onCreateView(): Int {
                    return R.layout.empty_view
                }

            })
        }

    }

}