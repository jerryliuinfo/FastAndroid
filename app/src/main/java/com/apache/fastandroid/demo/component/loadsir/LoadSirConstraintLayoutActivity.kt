package com.apache.fastandroid.demo.component.loadsir

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.databinding.ActivityLoadsirConstraintlayoutBinding
import com.apache.fastandroid.util.extensitons.runOnUIDelay
import com.apache.fastandroid.util.extensitons.showEmpty
import com.apache.fastandroid.util.extensitons.showLoading
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.activity_loadsir_constraintlayout.*

/**
 * Created by Jerry on 2021/12/16.
 */
class LoadSirConstraintLayoutActivity :
    BaseBindingFragment<ActivityLoadsirConstraintlayoutBinding>(ActivityLoadsirConstraintlayoutBinding::inflate) {

    private lateinit var loadService: LoadService<Any>


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        loadService = LoadSir.getDefault().register(tv_center,object :Callback.OnReloadListener{
            override fun onReload(v: View?) {

                // Your can change the status out of Main thread.
                Thread {
                    loadService.showLoading()
                    //do retry logic...
                    SystemClock.sleep(500)
                    //callback
                    loadService.showSuccess()
                }.start()
            }

        })
        runOnUIDelay({
            loadService.showEmpty()
        },2000)
    }
}