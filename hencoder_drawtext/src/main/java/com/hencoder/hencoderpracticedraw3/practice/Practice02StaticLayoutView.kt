package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/12/14.
 * 绘制多行的文字
 * StaticLayout 支持换行，它既可以为文字设置宽度上限来让文字自动换行，也会在 \n 处主动换行。
 */
class Practice02StaticLayoutView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {


    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
    }

    var text1 = "Lorem Ipsum is simply dummy text of the printing and typesetting industry."
    var text2 = "a\nbc\ndefghi\njklm\nnopqrst\nuvwx\nyz"

    private val staticLayout1 = StaticLayout.Builder.obtain(text1,0,text1.length,mTextPaint,600).build()

    private val staticLayout2 = StaticLayout.Builder.obtain(text2,0,text2.length,mTextPaint,600).build()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        staticLayout1.draw(canvas)

        canvas.save()
        canvas.translate(0f,300f)
        staticLayout2.draw(canvas)
        canvas.restore()
    }
}