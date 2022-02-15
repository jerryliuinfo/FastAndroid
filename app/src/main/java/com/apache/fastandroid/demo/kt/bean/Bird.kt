package com.apache.fastandroid.demo.kt.bean

/**
 * Created by Jerry on 2022/2/7.
 */
open class Bird(var name: String, val sex:Int = MAILE) {
    class InnerClass(var innerName:String){
        fun getName():String{
            //编译错误,报 Unresolved reference.
//            return "这是一只名字叫 ${name} 的鸟类 "
            return "这是一只名字叫 ${innerName} 的鸟类 "
        }
    }


    /**
     *  如果想让 嵌套类 能访问外部类的成员，可以在嵌套类的 class 前面加上 inner 关键字,
     *  这样就变成了内部类, 内部类比嵌套类的好处是能访问外部类的成员，所以 kotlin 的内部
     *  类就相当于 java 的嵌套，而 kotlin 的嵌套类则是加了访问限制
     */
    inner class InnerClass2(var innerName:String){
        fun getName():String{
            //编译错误,报 Unresolved reference.
            return "这是一只名字叫 ${name} 的鸟类 "
        }
    }


    companion object{
        val MAILE = 0
        val FEMAILE = 1
    }
}