package com.apache.fastandroid.demo.extension

/**
 * Created by Jerry on 2023/4/19.
 */
class ExtensionTest(var param1: String, var param2: String)

//扩展属性
var ExtensionTest.extensionParam: String
    set(value) {
        param1 = "param1$value"
        param1 = "param2$value"
    }
    get() = "$param1-$param2"
