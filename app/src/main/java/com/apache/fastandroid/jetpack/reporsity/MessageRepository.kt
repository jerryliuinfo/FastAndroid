package com.apache.fastandroid.jetpack.reporsity

import kotlin.random.Random

/**
 * Created by Jerry on 2021/2/7.
 */
class MessageRepository() {


    fun getList():List<String>{
        val value = 3000L
        val random = Random.nextLong(value)
        println("loadMessageHeader random time:$random")
        if (random > (value / 2)){
            Thread.sleep(random)
            throw java.lang.IllegalStateException("time out")
        }
        return listOf("zhangsan")
    }


}