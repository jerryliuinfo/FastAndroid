package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
class AnimalDefault @JvmOverloads constructor(name:String, sex:Int = 0){
    var sexName:String
    init {
        sexName = if (sex == 0) "公" else "母"
    }
}