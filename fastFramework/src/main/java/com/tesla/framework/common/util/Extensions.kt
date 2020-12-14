package com.tesla.framework.common.util
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue



val Float.dp
 get() = TypedValue.applyDimension(
         TypedValue.COMPLEX_UNIT_DIP,
         this,
         Resources.getSystem().displayMetrics
 )


val Int.dp
 get() = this.toFloat().dp


val Float.sp
 get() = TypedValue.applyDimension(
         TypedValue.COMPLEX_UNIT_SP,
         this,
         Resources.getSystem().displayMetrics
 )


val Int.sp
 get() = this.toFloat().sp

fun Paint.textCenterX(text:String, orginalCenterX:Float):Float{
  val rect = Rect()
  getTextBounds(text,0,text.length,rect)
  return orginalCenterX - rect.width() / 2
}
