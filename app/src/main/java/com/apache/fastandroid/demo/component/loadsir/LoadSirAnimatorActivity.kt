package com.apache.fastandroid.demo.component.loadsir

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.component.loadsir.callback.AnimateCallback
import com.apache.fastandroid.demo.component.loadsir.callback.EmptyCallback
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.apache.fastandroid.util.extensitons.showEmpty
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import java.util.*

/**
 * Created by  on 2021/12/18.
 */
class LoadSirAnimatorActivity:BaseStatusFragmentNew() {

    private lateinit var loadService: LoadService<Any>

    override fun getLayoutId(): Int {
        return R.layout.loadsir_activity_activity_convertor
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        // Your can change the callback on sub thread directly.
        val loadSir = LoadSir.Builder()
            .addCallback(EmptyCallback())
            .addCallback(AnimateCallback())
            .setDefaultCallback(AnimateCallback::class.java)
            .build()
        loadService = loadSir.register(activity) {
            // Your can change the status out of Main thread.
            Thread {
                loadService.showCallback(AnimateCallback::class.java)
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