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

/**
 * 此处的 inline 是不可被省略的，因为需要在编译器知道类型， 并且 T 需要加 refied 关键字，标记这个 T 是真泛型
 * 但是 refied 关键字只能修饰函数，不能修饰类
 */
inline fun <reified T> Gson.fromJson2(json:String):T{
    return fromJson(json, T::class.java)
}

inline fun <reified T> Context.getSystemService() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    getSystemService(T::class.java)
} else {
    null
}