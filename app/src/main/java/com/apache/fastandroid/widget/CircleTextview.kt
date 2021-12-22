package com.apache.fastandroid.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.drawable.CircleDrawable
import com.tesla.framework.kt.dpInt
import kotlin.math.min


/**
 * Description:TODO
 * Create Time:2021/12/21 10:16 下午
 * Author:jerry
 *
 */

class CircleTextview @JvmOverloads constructor(
	 context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)  {
	 private val DEFAULT_BG_COLOR = Color.BLACK

	 private val drawable = CircleDrawable()

	 init {

			var typeArray =
				 context.obtainStyledAttributes(attrs, R.styleable.CircleTextStyle, defStyleAttr, 0)

			var bgColor = typeArray.getColor(R.styleable.CircleTextStyle_CircleTextStyle_bgColor, DEFAULT_BG_COLOR)
			var textColor = typeArray.getColor(R.styleable.CircleTextStyle_CircleTextStyle_textColor, DEFAULT_BG_COLOR)
			var textSize = typeArray.getDimensionPixelSize(R.styleable.CircleTextStyle_CircleTextStyle_textSize, 40)
			var text = typeArray.getString(R.styleable.CircleTextStyle_CircleTextStyle_text_value)

			typeArray.recycle()

			drawable.setText(text)
			drawable.setTextColor(textColor)
			drawable.mTextPaint.textSize = textSize.toFloat()
			drawable.setBgColor(bgColor)
	 }

	 override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec)

			val widthSize = MeasureSpec.getSize(widthMeasureSpec)
			val heightSize = MeasureSpec.getSize(heightMeasureSpec);

			val size = min(widthSize,heightSize)
			setMeasuredDimension(size,size)

	 }

	 override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
			super.onSizeChanged(w, h, oldw, oldh)



			var min = min(w, h)
			drawable.setBounds(0,0, min,min)


	 }

	 override fun onDraw(canvas: Canvas) {
			super.onDraw(canvas)

			drawable.draw(canvas)
	 }

}