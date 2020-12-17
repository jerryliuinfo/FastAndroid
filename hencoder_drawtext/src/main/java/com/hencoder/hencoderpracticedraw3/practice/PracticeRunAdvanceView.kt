package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/12/17.
 */
class PracticeRunAdvanceView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val TEXT = "Hello HenCoder \uD83C\uDDE8\uD83C\uDDF3" //"Hello HenCoder 🇨🇳"

    private val offsetX = 100f

    private val offsetY = 100f

    private val distance = 60f

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        textSize = 20f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        val length = TEXT.length

        canvas.drawText(TEXT,offsetX,offsetY,mPaint)
        val advance1 = mPaint.getRunAdvance(TEXT,0,length,0,length,false,length)
        canvas.drawLine(offsetX+ advance1,offsetY - 50, offsetX + advance1, offsetX + 10,mPaint)


        canvas.translate(0f,distance)

        canvas.drawText(TEXT,offsetX,offsetY,mPaint)
        val advance2 = mPaint.getRunAdvance(TEXT,0,length,0,length,false,length - 1)
        canvas.drawLine(offsetX+ advance2,offsetY - 50, offsetX + advance2, offsetX + 10,mPaint)



        canvas.translate(0f,distance)

        canvas.drawText(TEXT,offsetX,offsetY,mPaint)
        val advance3 = mPaint.getRunAdvance(TEXT,0,length,0,length,false,length - 2)
        canvas.drawLine(offsetX+ advance3,offsetY - 50, offsetX + advance3, offsetX + 10,mPaint)



        canvas.translate(0f,distance)

        canvas.drawText(TEXT,offsetX,offsetY,mPaint)
        val advance4 = mPaint.getRunAdvance(TEXT,0,length,0,length,false,length - 3)
        canvas.drawLine(offsetX+ advance4,offsetY - 50, offsetX + advance4, offsetX + 10,mPaint)


        canvas.translate(0f,distance)

        canvas.drawText(TEXT,offsetX,offsetY,mPaint)
        val advance5 = mPaint.getRunAdvance(TEXT,0,length,0,length,false,length - 4)
        canvas.drawLine(offsetX+ advance5,offsetY - 50, offsetX + advance5, offsetX + 10,mPaint)

        canvas.restore()
    }
}