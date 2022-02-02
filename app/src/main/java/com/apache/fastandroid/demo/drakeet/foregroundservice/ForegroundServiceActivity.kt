package com.apache.fastandroid.demo.drakeet.foregroundservice

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import com.apache.fastandroid.databinding.FragmentCommonBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.activity.BaseVmActivity
import com.tesla.framework.ui.activity.BaseVmActivityNew

/**
 * Created by Jerry on 2021/10/18.
 */
class ForegroundServiceActivity:BaseVmActivityNew<FragmentCommonBinding>(FragmentCommonBinding::inflate) {
    private lateinit var msg:String

    private val handler = Handler()




    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        startService()
    }

    private fun startService(){
        handler.postDelayed({
               val intent = Intent(activity,InhibitorService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                activity?.startForegroundService(intent)
                Logger.d("startForegroundService -->")
                startService()
            }

        }, 200)
    }

    fun TextView.prepend(string: String){
        text = string + text
    }




}