package com.hencoder.hencoderpracticedraw4.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.hencoderpracticedraw4.R

/**
 * Created by Jerry on 2021/9/21.
 */
class Practice05RotateView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.maps)
    var point1 = Point(200, 200)
    var point2 = Point(600, 200)
    var point3 = Point(1000, 200)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        canvas.drawBitmap(bitmap,(point1.x + bitmap.width /2).toFloat(), (point1.y + bitmap.height / 2).toFloat(), paint)

        canvas.save()
        canvas.rotate(180f, (point2.x + bitmap.width /2).toFloat(), (point2.y + bitmap.height /2).toFloat())
        canvas.drawBitmap(bitmap,point2.x.toFloat(), point2.y.toFloat(), paint)
        canvas.restore()


        canvas.save()
        canvas.rotate(45f, (point3.x + bitmap.width /2).toFloat(), (point3.y + bitmap.height /2).toFloat())

        canvas.drawBitmap(bitmap,point3.x.toFloat(), point3.y.toFloat(), paint)
        canvas.restore()

    }
}
