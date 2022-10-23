package com.apache.fastandroid.demo.kt.bean

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/1/23.
 */
class KotlinMain {

    private var kotlinMain:KotlinMain ?= null

    companion object{
        val instance = KotlinMain()
    }

    inline fun onlyIf2(debug:Boolean, block:() -> Unit){
        if (debug){
            block()
        }
    }


    fun getKotlinMain():KotlinMain{
        if (kotlinMain == null){
            kotlinMain = KotlinMain().also {
                kotlinMain = it
            }
        }
        return kotlinMain!!
    }

    /**
     * Java 调用 kotlin 如果传了 为null的参数过来将会报错
     *
     */
    fun notAllowNull(str:String){
        Logger.d("notAllowNull str:$str")
    }

    fun allowNull(str:String?){
        Logger.d("allowNull str:$str")
    }


}

fun main(args: Array<String>) {
    println("hello world")
}