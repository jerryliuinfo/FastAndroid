package com.apache.fastandroid.artemis.ui.app

import android.app.Application

/**
 * Created by Jerry on 2023/2/20.
 */
open class ComApplication:Application() {

    private lateinit var sInstance:ComApplication

    override fun onCreate() {
        super.onCreate()

        sInstance = this

    }

    fun getInstance():ComApplication{
        return sInstance
    }
}