package com.apache.fastandroid.demo.component.loadsir

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.LoadsirActivityEmptyBinding
import com.apache.fastandroid.demo.component.loadsir.callback.EmptyCallback
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.apache.fastandroid.util.extensitons.showLoading
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.kingja.loadsir.core.Transport
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlin.concurrent.thread

/**
 * Created by Jerry on 2021/12/16.
 */
class LoadSirActivityEmptyFragment:BaseVBFragment<LoadsirActivityEmptyBinding>(LoadsirActivityEmptyBinding::inflate) {

    private lateinit var loadService: LoadService<Any>

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        loadService = LoadSir.getDefault().register(activity,object :Callback.OnReloadListener{
            override fun onReload(v: View) {
                thread {
                    loadService.showLoading()
                    SystemClock.sleep(500)
                    loadService.showSuccess()
                }
            }

        }).setCallBack(EmptyCallback::class.java,object :Transport{
            override fun order(context: Context?, view: View) {
                var textView = view.findViewById<TextView>(R.id.tv_empty)
                textView.text = "fine, no data. You must fill it!"
            }

        })
       /* HandlerUtil.getUIHandler().postDelayed({
            loadService.showCallback(EmptyCallback::class.java)
        },1000)*/
        runOnUIDelay({
            loadService.showCallback(EmptyCallback::class.java)
        },1000)
    }

}