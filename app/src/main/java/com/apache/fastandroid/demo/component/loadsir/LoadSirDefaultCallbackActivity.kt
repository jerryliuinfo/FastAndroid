package com.apache.fastandroid.demo.component.loadsir

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.kingja.loadsir.callback.HintCallback
import com.kingja.loadsir.callback.ProgressCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import java.util.*

/**
 * Created by  on 2021/12/18.
 */
class LoadSirDefaultCallbackActivity:BaseStatusFragmentNew() {
    private lateinit var loadService: LoadService<Any>

    override fun getLayoutId(): Int {
        return R.layout.loadsir_activity_activity_convertor
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val loadingCallback = ProgressCallback.Builder()
            .setTitle(
                "Loading",
                R.style.loadsir_Hint_Title
            ) //                .setAboveSuccess(true)// attach loadingView above successView
            .build()

        val hintCallback = HintCallback.Builder()
            .setTitle("Error", R.style.loadsir_Hint_Title)
            .setSubTitle("Sorry, buddy, I will try it again.")
            .setHintImg(R.drawable.error)
            .build()

        val loadSir = LoadSir.Builder()
            .addCallback(loadingCallback)
            .addCallback(hintCallback)
            .setDefaultCallback(ProgressCallback::class.java)
            .build()

        loadService = loadSir.register(activity) {
            loadService.showCallback(ProgressCallback::class.java)
            runOnUIDelay({
                         loadService.showSuccess()
            },2000)
        }
        runOnUIDelay({
                     loadService.showCallback(HintCallback::class.java)
        },2000)


    }


}