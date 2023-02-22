package com.apache.fastandroid.jetpack.reporsity

import kotlin.random.Random

/**
 * Created by Jerry on 2021/2/7.
 */
class MessageRepository() {


    fun getList():List<String>{
        val random = Random.nextLong(1000)
        println("loadMessageHeader random time:$random")
        Thread.sleep(random)
        if (random > 800){
           throw java.lang.IllegalStateException("time out")
        }
        return listOf("zhangsan")
    }


}