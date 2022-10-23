package com.apache.fastandroid.demo.kt.operatoroverload

/**
 * Created by Jerry on 2022/2/20.
 */
class Choir {
     val singers = mutableListOf<Singer>()


    fun addSinger(singer: Singer){
        singers.add(singer)
    }

    operator fun plusAssign(singer: Singer){
        singers.add(singer)
    }


    operator fun contains(singer: Singer):Boolean{
        return singers.contains(singer)
    }




}

