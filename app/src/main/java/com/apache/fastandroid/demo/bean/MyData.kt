package com.apache.fastandroid.demo.bean

/**
 * Created by Jerry on 2023/5/11.
 */
sealed class MyData{

    data class DataType1(val text:String):MyData()

    data class DataType2(val text:String):MyData()
}
