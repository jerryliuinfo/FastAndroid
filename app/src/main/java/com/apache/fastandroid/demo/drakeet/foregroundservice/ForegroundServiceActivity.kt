package com.apache.fastandroid.demo.drakeet.foregroundservice

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.apache.fastandroid.R
import com.orhanobut.logger.Logger
import com.tesla.framework.ui.activity.BaseActivity
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class ForegroundServiceActivity:BaseActivity() {
    private lateinit var msg:String

    private val handler = Handler()
    override fun getLayoutId(): Int {
        return R.layout.fragment_common
    }



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