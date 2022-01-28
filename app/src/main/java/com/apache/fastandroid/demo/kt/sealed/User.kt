package com.apache.fastandroid.demo.kt.sealed

/**
 * Created by Jerry on 2022/1/27.
 */
data class User(var id:Int, var name:String, var playerType:PlayerViewType = PlayerViewType.BLUE)