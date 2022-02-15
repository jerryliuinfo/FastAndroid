package com.apache.fastandroid.demo.kt.sealed

/**
 * Created by Jerry on 2022/2/7.
 */
sealed class SeasonNameSealed() {
    class Spring(var name:String): SeasonNameSealed()
    class Summer(var name:String): SeasonNameSealed()
    class Autumn(var name:String): SeasonNameSealed()
    class Winter(var name:String): SeasonNameSealed()
}