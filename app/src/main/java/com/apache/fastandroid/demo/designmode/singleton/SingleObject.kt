package com.apache.fastandroid.demo.designmode.singleton

/**
 * Created by Jerry on 2022/1/27.
 */
class SingleObject private constructor(){

    companion object{
        fun getInstance(): SingleObject {
            return Holder.instance
        }
    }

    private object Holder{
        val instance = SingleObject()
    }

}


class SingleInstanceSync {

    companion object{
        @Volatile
        private var sInstance:SingleInstanceSync ?= null
        fun getInstance():SingleInstanceSync{
            return sInstance ?: synchronized(SingleInstanceSync::class.java) {
                sInstance?: SingleInstanceSync().also {
                    sInstance = it
                }
            }
        }

        fun getInstance2():SingleInstanceSync{
            return sInstance ?: SingleInstanceSync().also {
                    sInstance = it
                }
        }
    }
}


/**
 * 采用 object 关键字实现单例(恶汉模式)
 */

object SingletonByObject {

    private var count:Int = 0

    fun count(){
        count ++
    }

}