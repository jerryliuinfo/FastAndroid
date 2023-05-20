package com.apache.fastandroid.demo.decoration

import android.content.Context
import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.dp
import com.tesla.framework.kt.dpInt

/**
 * Created by Jerry on 2021/5/3.
 */
class CommentItemDecoration(val context:Context): RecyclerView.ItemDecoration() {
    private val marginHorizontal = 2.dp
    private var RADIS = 10f.dp

    private val mCirclePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.RED
        isAntiAlias = true
    }

    private val mRoundPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f
        color = Color.RED
    }

    private var  bitmap:Bitmap
    init {
        bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.about_page_card)
    }


    /**
     * onDraw() 函数中的 parent , state 参数和 getItemOffsets() 方法中的参数含义是一样的，canvas 参数是 getItemOffsets() 函数所留下的左右上下的空白区域对应的 Canvas 画布对象。
     * 我们可以在这个区域中(getItemOffsets 中留下的空白区域)利用 Paint 画笔绘制任何图形。
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        var layoutManager = parent.layoutManager!!

        for (index in 0 until parent.childCount){
          var childView = parent.getChildAt(index)
            //getOffset 中的 outRect 左边的间距
            var leftDecorationWidth = layoutManager.getLeftDecorationWidth(childView)
            var rightDecorationWidth = layoutManager.getRightDecorationWidth(childView)
            var topDecorationHeight = layoutManager.getTopDecorationHeight(childView)
            var bottomDecorationHeight = layoutManager.getBottomDecorationHeight(childView)
            Logger.d("onDraw leftDecorationWidth:${leftDecorationWidth}, rightDecorationWidth:${rightDecorationWidth}")

             leftDecorationWidth = childView.left
            val centerX = leftDecorationWidth + RADIS * 2 + mCirclePaint.strokeWidth / 2 + parent.paddingLeft
            c.drawCircle(centerX, ((childView.top + childView.bottom) / 2).toFloat(), RADIS,mCirclePaint)


            c.drawLine(leftDecorationWidth.toFloat(),((childView.bottom + childView.top)/2).toFloat(),
                childView.right.toFloat(), ((childView.bottom + childView.top)/2).toFloat(),mRoundPaint)
      }

    }

    /**
     * 绘制在图的最上层
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        for (index in 0 until parent.childCount){
            val child = parent.getChildAt(index)

            val rect = Rect((child.right - bitmap.width),child.top,child.right,child.bottom)
            c.drawBitmap(bitmap,(child.right - bitmap.width).toFloat(),child.top.toFloat(),mCirclePaint)
        }
    }


    /**
     *
     * outRect:表示 item 的上下左右所留下的边距。其中 outRect 的 left, top,right,bottom 即为 item 四周留下的边距的距离，默认都为 0
     * View view : 指当前 item 的 View 对象；
     * parent : 指 RecyclerView 本身；
     * state:指 RecyclerView 当前的状态；
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        var positon = parent.getChildLayoutPosition(view)
        if (positon == 0){
            outRect.top = 0.dpInt
        }else{
            outRect.top = 10.dpInt
        }
        outRect.left = 30.dpInt
        outRect.right = 30.dpInt

    }
}