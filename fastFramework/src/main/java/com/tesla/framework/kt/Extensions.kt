package com.tesla.framework.kt

import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue
import kotlin.math.pow

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
