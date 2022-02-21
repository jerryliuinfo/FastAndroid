package com.apache.fastandroid.demo.kt.inline

import com.apache.fastandroid.demo.bean.UserBean

/**
 * Created by Jerry on 2022/2/18.
 */

interface Id

/**
 * 限制
 * 有且只能有一个参数
 * 没有 backing fields
 * 不能有init 块
 * 不能继承其他类（但是可以继承接口）
 *
 * 内联类可以做到
 * 从接口继承
 * 具有属性和方法
 */
inline class DoggoId(val id:Long):Id{
    init {

    }
    val stringId
        get() = id.toString()


    fun isValid() = id > 0
}

data class Doggo(val id:DoggoId)

val goodDoggo = Doggo(DoggoId(10))

fun pet(id:DoggoId){

}