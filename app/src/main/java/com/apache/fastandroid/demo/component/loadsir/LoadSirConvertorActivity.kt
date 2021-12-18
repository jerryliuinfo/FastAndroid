package com.apache.fastandroid.demo.component.loadsir

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.component.loadsir.callback.EmptyCallback
import com.apache.fastandroid.demo.component.loadsir.callback.ErrorCallback
import com.apache.fastandroid.demo.component.loadsir.callback.LoadingCallback
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.Convertor
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by  on 2021/12/18.
 */
class LoadSirConvertorActivity:BaseStatusFragmentNew() {
    private val mHttpResult = HttpResult(Random().nextInt(2), ArrayList())
    private val SUCCESS_CODE = 0x00
    private val ERROR_CODE = 0x01
    private lateinit var loadService: LoadService<HttpResult>

    override fun inflateContentView(): Int {
        return R.layout.loadsir_activity_activity_convertor
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val loadSir = LoadSir.Builder()
            .addCallback(LoadingCallback())
            .addCallback(EmptyCallback())
            .addCallback(ErrorCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .build()

        loadService = loadSir.register(activity,{
            thread {
                loadService.showCallback(LoadingCallback::class.java)
                SystemClock.sleep(500)
                loadService.showSuccess()
            }
        }, object :Convertor<HttpResult>{
            override fun map(httpResult: HttpResult): Class<out Callback> {
                return when (httpResult.resultCode){
                    SUCCESS_CODE -> if (httpResult.data.isEmpty()) EmptyCallback::class.java else SuccessCallback::class.java
                    else -> ErrorCallback::class.java
                }
            }

        }) as LoadService<HttpResult>

        runOnUIDelay({
                  loadService.showWithConvertor(mHttpResult)
        }, 1000)


    }

    private class HttpResult internal constructor(val resultCode: Int, val data: List<Any>)

}