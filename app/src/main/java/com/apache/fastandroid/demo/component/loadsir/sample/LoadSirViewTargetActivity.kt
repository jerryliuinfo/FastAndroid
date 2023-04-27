package com.apache.fastandroid.demo.component.loadsir.sample

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.LoadsirActivityViewBinding
import com.apache.fastandroid.demo.component.loadsir.sample.callback.LoadingCallback
import com.apache.fastandroid.demo.component.loadsir.sample.callback.TimeoutCallback
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.apache.fastandroid.util.extensitons.showLoading
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.loadsir_activity_view.*

/**
 * Created by  on 2021/12/18.
 */
class LoadSirViewTargetActivity:BaseBindingFragment<LoadsirActivityViewBinding>(LoadsirActivityViewBinding::inflate) {

    private lateinit var loadService: LoadService<Any>

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val loadSir = LoadSir.Builder()
            .addCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.TimeoutCallback())
            .addCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.LoadingCallback())
            .setDefaultCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.LoadingCallback::class.java)
            .build()
        loadService = loadSir.register(iv_img) {
            loadService.showLoading()

            runOnUIDelay({ loadService.showSuccess() }, 2000)
        }
        runOnUIDelay({
            loadService.showCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.TimeoutCallback::class.java)

        },2000)

        Logger.d("hello")


    }
}