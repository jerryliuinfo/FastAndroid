package com.tesla.framework.kt

import android.content.Context
import android.os.Build
import com.google.gson.Gson
import java.lang.IllegalStateException

/**
 * Created by Jerry on 2023/1/5.
 */





inline fun <R> myRun(block:() -> R):R{

    return block()
}

inline fun <T,R> T.myRun(block: T.() -> R):R{

    return block(this)
}

inline fun <T> T.myApply(block:T.() -> Unit):T{
//    block.invoke(this)
    block()
    return this
}


inline fun <T,R> T.myWith(receiver:T, block:T.() -> R):R{
    return block(this)
}

inline fun myWith(name: String, block: String.() -> Unit){
    name.block()
}

inline fun <T> T.myAlso(block: (T) -> Unit):T{
    block(this)
    return this
}

inline fun <T,R> T.myLet(block:(T) -> R):R{
    return block(this)
}






inline fun <T> T.myRepeats(times:Int, action:(T,Int) -> Unit ){
    for (index in 0 until times){
        action(this,index)
    }
}

inline fun myCheck(value:Boolean, lazyMessage:() -> Any):Unit{
    if (!value){
        val message = lazyMessage()
        throw IllegalStateException(message.toString())
    }
}


inline fun Int.myCoerAtLeast(miniNumValue:Int):Int{
    return if (this < miniNumValue) miniNumValue else this
}


inline fun Int.myCoerAtMost(maxiNumValue:Int):Int{
    return if (this > maxiNumValue) maxiNumValue else this
}


inline fun <T> T.myOnlyIf(value:Boolean, block:T.() -> Unit){
    if (value){
        block()
    }
}

/**
 * 满足条件返回 this， 否则返回 null
 */
inline  fun <T> T.myTakeIf(predicate: (T) -> Boolean):T?{
    return if (predicate(this)) this else null
}

/**
 * 不满足条件，返回 this，否则返回 null
 */
inline fun <T> T.myTakeUnless(predicate:(T) -> Boolean):T?{
    return if (!predicate(this)) this else null
}


private fun <T> myfilter(originalList: List<T>, condition: (T) -> Boolean): List<T> {
    val list = mutableListOf<T>()
    for (t in originalList) {
        if (condition(t)) {
            list.add(t)
        }
    }
    return list
}

private fun <T, R> myMap(originalList: List<T>, transform: (T) -> R): List<R> {
    val list = mutableListOf<R>()
    for (t in originalList) {
        list.add(transform(t))
    }
    return list
}


fun <T, R> Iterable<T>.myMap(transform: (T) -> R): List<R> {
    return myMap(ArrayList<T>(), transform)
}

fun <T> Iterable<T>.myFilter(preCondtion: (T) -> Boolean): List<T> {
    return myfilter(ArrayList<T>(), preCondtion)
}


/**
 * 此处的 inline 是不可被省略的，因为需要在编译器知道类型， 并且 T 需要加 refied 关键字，标记这个 T 是真泛型
 * 但是 refied 关键字只能修饰函数，不能修饰类
 */
inline fun <reified T> Gson.fromJson2(json:String):T{
    return fromJson(json, T::class.java)
}

inline fun <reified T> Context.getMySystemService() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    getSystemService(T::class.java)
} else {
    null
}