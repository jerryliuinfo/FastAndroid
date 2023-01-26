package com.apache.fastandroid.demo.kt.enum

/**
 * Created by Jerry on 2022/4/26.
 */
enum class State {
    IDLE,
    RUNNING,
    FINISHED,
}


enum class Grade(private val age:Int, private val desc:String) {
    PRIMARY(6, "小学"),
    MIDDLE(9,"中学"),
    University(18, "大学"),
}