package com.apache.fastandroid.demo.kt.singleton

/**
 * Created by Jerry on 2022/6/2.
 */
class SingleInstanceSync {

    companion object{

        private var sInstance:SingleInstanceSync ?= null

        fun getInstance():SingleInstanceSync{
            return sInstance ?: synchronized(SingleInstanceSync::class.java) {
                sInstance?: SingleInstanceSync().also {
                    sInstance = it
                }
            }
        }
    }
}