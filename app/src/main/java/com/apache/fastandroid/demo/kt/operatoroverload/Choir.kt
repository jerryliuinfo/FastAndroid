package com.apache.fastandroid.demo.kt.operatoroverload

/**
 * Created by Jerry on 2022/2/20.
 */
class Choir {
     val singers = mutableListOf<Singer>()

    //传统方式
    fun addSinger(singer: Singer){
        singers.add(singer)
    }

    //操作符重载方式，外部可以通过 += 的方式来调用
    operator fun plusAssign(singer: Singer){
        singers.add(singer)
    }



    operator fun contains(singer: Singer):Boolean{
        return singers.contains(singer)
    }




}

