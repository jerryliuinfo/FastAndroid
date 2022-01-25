package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/1/23.
 */
class KotlinMain {

    companion object{
        val instance = KotlinMain()
    }

    inline fun onlyIf2(debug:Boolean, block:() -> Unit){
        if (debug){
            block()
        }
    }


}