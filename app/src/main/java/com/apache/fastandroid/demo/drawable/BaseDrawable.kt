package com.apache.fastandroid.demo.drawable

import android.graphics.*
import android.graphics.drawable.Drawable


/**
 * Description:TODO
 * Create Time:2021/12/21 10:14 下午
 * Author:jerry
 *
 */

abstract class BaseDrawable : Drawable() {
	 protected val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
	 	 style = Paint.Style.FILL
	 }

	 override fun getIntrinsicWidth(): Int {
			return bounds.width().toInt()
	 }

	 override fun getIntrinsicHeight(): Int {
			return bounds.height().toInt()
	 }

	 override fun setAlpha(alpha: Int) {
			paint.alpha = alpha
			invalidateSelf()
	 }

	 override fun setColorFilter(colorFilter: ColorFilter?) {
			paint.colorFilter = colorFilter
			invalidateSelf()
	 }

	 override fun getOpacity(): Int {
			return PixelFormat.TRANSLUCENT
	 }

}