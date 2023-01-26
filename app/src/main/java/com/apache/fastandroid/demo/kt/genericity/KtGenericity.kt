package com.apache.fastandroid.demo.kt.genericity

import android.content.Context
import android.os.Build
import com.google.gson.Gson

/**
 * Created by Jerry on 2022/1/30.
 */
class KtGenericity<T> where T:GeCallback, T:Runnable{

    fun add(t:T){
        t.run()
        t.callback()
    }




}


interface GeCallback{
    fun callback()
}

class GenericityAImpl:GeCallback,Runnable{
    override fun callback() {
        println("GenericityAImpl callback")
    }

    override fun run() {
        println("GenericityAImpl run")

    }

}

