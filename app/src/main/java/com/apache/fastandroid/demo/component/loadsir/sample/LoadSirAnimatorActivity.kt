package com.apache.fastandroid.demo.component.loadsir.sample

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.LoadsirActivityActivityConvertorBinding
import com.apache.fastandroid.demo.component.loadsir.sample.callback.AnimateCallback
import com.apache.fastandroid.demo.component.loadsir.sample.callback.EmptyCallback
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.apache.fastandroid.util.extensitons.showEmpty
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by  on 2021/12/18.
 */
class LoadSirAnimatorActivity:BaseBindingFragment<LoadsirActivityActivityConvertorBinding>(LoadsirActivityActivityConvertorBinding::inflate) {

    private lateinit var loadService: LoadService<Any>


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        // Your can change the callback on sub thread directly.
        val loadSir = LoadSir.Builder()
            .addCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.EmptyCallback())
            .addCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.AnimateCallback())
            .setDefaultCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.AnimateCallback::class.java)
            .build()
        loadService = loadSir.register(activity) {
            // Your can change the status out of Main thread.
            Thread {
                loadService.showCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.AnimateCallback::class.java)
                //do retry logic...
                SystemClock.sleep(500)
                //callback on sub thread
                loadService.showSuccess()
            }.start()
        }
        runOnUIDelay({
                     loadService.showEmpty()
        },1000)

    }


}