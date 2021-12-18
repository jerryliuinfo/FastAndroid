package com.apache.fastandroid.demo.adapter

import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ConvertUtils
import com.tesla.framework.kt.dpInt

/**
 * Created by Jerry on 2021/5/3.
 */
class CommentItemDecoration: RecyclerView.ItemDecoration() {
    private val marginHorizontal = ConvertUtils.dp2px(2f)

    private val mCirclePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.BLUE
        isAntiAlias = true
    }

    private val mRoundPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f
        color = Color.RED
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
            var leftDecorationWidth = layoutManager.getLeftDecorationWidth(childView)

            var topDecorationHeight = layoutManager.getTopDecorationHeight(childView);
            var rightDecorationWidth = layoutManager.getRightDecorationWidth(childView);
            var bottomDecorationHeight = layoutManager.getBottomDecorationHeight(childView);


            val left = ConvertUtils.dp2px(leftDecorationWidth / 2f) - mCirclePaint.strokeWidth - marginHorizontal
            c.drawCircle(left, ((childView.top + childView.bottom) / 2).toFloat(), 20f,mCirclePaint)

            val rouncRect = RectF(leftDecorationWidth.toFloat(), childView.bottom.toFloat(), (childView.getWidth() + leftDecorationWidth).toFloat(), (childView.bottom+ bottomDecorationHeight).toFloat())

            c.drawRoundRect(rouncRect,5f,5f,mRoundPaint)
      }

    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
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
            outRect.top = 40.dpInt
        }else{
            outRect.top = 10.dpInt
        }


        outRect.left = 50.dpInt
        outRect.right = 30.dpInt
    }
}