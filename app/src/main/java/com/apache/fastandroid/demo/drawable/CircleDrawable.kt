package com.apache.fastandroid.demo.drawable

import android.graphics.Canvas
import android.graphics.Paint.FontMetricsInt
import android.text.TextPaint
import androidx.annotation.ColorInt
import com.tesla.framework.kt.textCenterX
import com.tesla.framework.kt.textCenterY


/**
 * Description:TODO
 * Create Time:2021/12/21 10:13 下午
 * Author:jerry
 *
 */

class CircleDrawable:BaseDrawable() {
	 internal  val mTextPaint = TextPaint()

	 override fun draw(canvas: Canvas) {

	 	 canvas.drawCircle((bounds.width()/2).toFloat(),
				(bounds.height()/2).toFloat(), (bounds.width() /2).toFloat(), paint)
			text?.let {

				 val fontMetrics: FontMetricsInt = mTextPaint.getFontMetricsInt()
				 canvas.drawText(it, mTextPaint.textCenterX(it, (bounds.width()/2).toFloat()), mTextPaint.textCenterY(it,
						(bounds.height()/2).toFloat()
				 ),mTextPaint )
			}

	 }

	 private var text:String? = null

	 fun setText(text: String?){
	 	 this.text = text
			invalidateSelf()
	 }

	 fun setTextColor(@ColorInt color: Int){
			mTextPaint.setColor(color)
			invalidateSelf()
	 }

	 fun setBgColor(@ColorInt color: Int){
			paint.setColor(color)
	 }

}