package com.tesla.framework.kt

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import androidx.annotation.StringRes
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.Utils
import com.tesla.framework.component.livedata.NetworkLiveData
import kotlin.math.pow

/**
 * dp转换成px
 */
fun Context.dp(dpValue: Float): Float {
    var scale = resources.displayMetrics.density;
    return dpValue * scale + 0.5f
}

fun Context.dpInt(dpValue: Float): Int {
    return dp(dpValue).toInt()
}

val Float.dp
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )


val Int.dp
    get() = this.toFloat().dp

val Int.dpInt
    get() = this.dp.toInt()

val Float.sp
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )
val Int.sp
    get() = this.toFloat().sp


fun Float.powWrapp(n: Int): Float = this.pow(n)

val Int.getString
    get()  = Utils.getApp().getString(this)

fun Int.getName(): String {
    return when (this) {
        1 -> "AAA"
        2 -> "BBB"
        3 -> "CCC"
        else -> "AAA"
    }
}

fun Int.getValue(): String = kotlin.run {
    when (this) {
        1 -> "AAA"
        2 -> "BBB"
        3 -> "CCC"
        else -> "AAA"
    }
}

fun Paint.textCenterX(text:String, orginalCenterX: Float):Float{
    val rect = Rect()
    getTextBounds(text,0,text.length,rect)
    return (orginalCenterX - rect.width() / 2).toFloat()
}

fun Paint.textCenterY(text:String, baseY:Float):Float{
    val rect = Rect()
    getTextBounds(text,0,text.length,rect)
    return baseY + rect.height() / 2
}

fun Int.networkStatus(): String {
    return when(this){
        NetworkLiveData.NetworkState.NONE -> "未连接"
        NetworkLiveData.NetworkState.CONNECT -> "已连接"
        NetworkLiveData.NetworkState.WIFI -> "WIFI"
        NetworkLiveData.NetworkState.CELLULAR -> "移动网络"
        else -> "未知"
    }
}


/**
 * 这个方法但是定义在文件里面的，java中通过 ExtensionsKt.echo() 调用， 定义在文件中的方法最终都会变成public static 的方法和变量
 */
fun echo(){

}

inline fun inlineFunction(msg:String){
    println("inlne:${msg}")
}

fun Array<Int>.swap(pos1:Int, pos2:Int){
    val tmp = this[pos1]
    this[pos1] = this[pos2]
    this[pos2] = tmp


}

fun <T> Array<T>.maxCustomize(greater:(T,T) -> Boolean):T?{
    var max: T? = null
    for (item in this){
        if (max == null || greater(item,max)){
            max = item
        }
    }
    return max
}

