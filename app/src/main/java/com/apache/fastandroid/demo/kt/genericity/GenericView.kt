package com.apache.fastandroid.demo.kt.genericity

import android.view.View

/**
 * Created by Jerry on 2022/1/30.
 */
class GenericView<T> (val clazz: Class<T>) {

    val presenter by lazy { clazz.newInstance() }

    class Presenter{
        fun test(){
            println("Presenter test")
        }
    }

    /**
     * 伴生对象会在构造函数之前，类加载器加载类的时候就创建好
     */
    companion object{
        //重载 invoke 运算符
        inline operator fun <reified T> invoke() = GenericView(T::class.java)
    }


}