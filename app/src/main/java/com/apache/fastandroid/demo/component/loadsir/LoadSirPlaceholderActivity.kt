package com.apache.fastandroid.demo.component.loadsir

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.component.loadsir.callback.PlaceholderCallback
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 * Created by  on 2021/12/18.
 */
class LoadSirPlaceholderActivity:BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.loadsir_activity_placeholder
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val loadSir:LoadSir = LoadSir.Builder().apply {
            addCallback(PlaceholderCallback())
            setDefaultCallback(PlaceholderCallback::class.java)
        }.build()
        //do retry logic...
        val loadService  = loadSir.register(activity) {

        }
        runOnUIDelay({
                  loadService.showSuccess()
        }, 1000)


    }

}