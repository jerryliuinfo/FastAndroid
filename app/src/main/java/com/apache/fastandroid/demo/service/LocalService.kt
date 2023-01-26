package com.apache.fastandroid.demo.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlin.random.Random

/**
 * Created by Jerry on 2023/1/1.
 */
class LocalService:Service() {

    private val binder = LocalBinder()

    private val mGenerator = java.util.Random()

    val randomNumber:Int
        get() = mGenerator.nextInt(100)

    inner class LocalBinder:Binder(){

        fun getService():LocalService{
            return this@LocalService
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


}