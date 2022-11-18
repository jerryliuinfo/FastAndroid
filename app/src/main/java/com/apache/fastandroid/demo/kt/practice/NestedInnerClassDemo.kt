package com.apache.fastandroid.demo.kt.practice

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/11/7.
 */
class NestedInnerClassDemo {

    /**
     * 嵌套类
     */
    class Nested{

        fun test(){
            Logger.d("Nested test--->")
        }

    }


    /**
     * 内部类
     */
    inner class Inner{

        fun test(){
            Logger.d("Inner test--->")
        }
    }
}