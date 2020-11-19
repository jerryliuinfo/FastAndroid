package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.hencoder.paint.R


/**
 * Created by Jerry on 2020/11/13.
 */
class Practice05ComposeShaderView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // 第一个 Shader：头像的 Bitmap
        val shader1: Shader = BitmapShader(BitmapFactory.decodeResource(resources, R.drawable.batman), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        // 第二个 Shader：从上到下的线性渐变（由透明到黑色）
        val shader2: Shader = BitmapShader(BitmapFactory.decodeResource(resources, R.drawable.batman_logo), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        // ComposeShader：结合两个 Shader
        shader = ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_OVER)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(300f,300f,300f,mPaint)
    }
}