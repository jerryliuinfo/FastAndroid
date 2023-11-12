package com.seiko.demo.base

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.apache.fastandroid.demo.drakeet.customview.CustomLayoutExtensions

abstract class CustomLayout(
    context: Context,
    attrs: AttributeSet? = null
) : ViewGroup(context, attrs), CustomLayoutExtensions {

    companion object {
        const val WRAP_CONTENT = MarginLayoutParams.WRAP_CONTENT
        const val MATCH_PARENT = MarginLayoutParams.MATCH_PARENT
    }

    protected fun <T : View> T.autoAddView(
        width: Int = WRAP_CONTENT,
        height: Int = WRAP_CONTENT,
        block: T.(LayoutParams) -> Unit = {}
    ): T = apply {
        val mLayoutParams = LayoutParams(width, height)
        block(mLayoutParams)
        layoutParams = mLayoutParams
        this@CustomLayout.addView(this)
    }

    protected fun <T : View> T.autoAddView(
        size: Int,
        block: T.(LayoutParams) -> Unit = {}
    ) = autoAddView(size, size, block)

    protected fun <T : View> T.autoAddViewMax(
        width: Int = MATCH_PARENT,
        height: Int = MATCH_PARENT,
        block: T.(LayoutParams) -> Unit = {}
    ) = autoAddView(width, height, block)

    class LayoutParams : MarginLayoutParams {
        constructor(size: Int) : super(size, size)
        constructor(width: Int = WRAP_CONTENT, height: Int = WRAP_CONTENT) : super(width, height)
    }

    /**
     * 自动测量
     */
    protected fun View.autoMeasure(
        widthMeasureSpec: Int = defaultWidthMeasureSpec(parentView = this@CustomLayout),
        heightMeasureSpec: Int = defaultHeightMeasureSpec(parentView = this@CustomLayout)
    ) {
        measure(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * 多View自动测量
     */
    protected fun autoMeasure(vararg views: View) {
        views.forEach { it.autoMeasure() }
    }

    /**
     * 布局
     */
    protected fun View.layout(x: Int, y: Int) = layout(
        x, y, x + measuredWidth, y + measuredHeight
    )

    /**
     * 布局
     * @param fromRight 从右侧开始
     * @param fromBottom 从底部开始
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun View.layout(
        x: Int, y: Int,
        fromRight: Boolean = false,
        fromBottom: Boolean = false
    ) = layout(
        if (fromRight) this@CustomLayout.measuredWidth - x - measuredWidth else x,
        if (fromBottom) this@CustomLayout.measuredHeight - y - measuredHeight else y
    )

    /**
     * 将某个 View 放到父布局的中心(水平和垂直)
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun View.layoutCenter() = layout(
        (this@CustomLayout.measuredWidth - measuredWidth) / 2,
        (this@CustomLayout.measuredHeight - measuredHeight) / 2
    )

    /**
     * 将某个 View 放到 targetView 的中心(水平和垂直)
     * @param target 目标View
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun View.layoutCenter(target: View) = layout(
        target.left + (target.measuredWidth - measuredWidth) / 2,
        target.top + (target.measuredHeight - measuredHeight) / 2
    )


    /**
     * 多View居中绘制（包括水平和垂直方向都居中）
     * @param isVertical 是否垂直排列
     */
    protected fun layoutCenter(vararg views: View, isVertical: Boolean = true) {
        if (isVertical) {
            val topY = (measuredHeight - plusHeightWithMargins(*views)) / 2
            layoutVertical(topY, *views)
        } else {
            val leftX = (measuredWidth - plusWidthWithMargins(*views)) / 2
            layoutHorizontal(leftX, *views)
        }
    }


    /**
     * 在父布局中垂直居中，左右移动  ← →
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun View.layoutHorizontal(x: Int, fromRight: Boolean = false) = layout(
        x = x,
        y = (this@CustomLayout.measuredHeight - measuredHeight) / 2,
        fromRight = fromRight
    )

    /**
     * 与目标View垂直居中，左右移动  ← →
     * @param target 目标View
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun View.layoutHorizontal(
        x: Int, target: View, fromRight: Boolean = false
    ) = layout(
        x = x,
        y = target.top + (target.measuredHeight - measuredHeight) / 2,
        fromRight = fromRight
    )

    /**
     * 横向居中，上下移动 ↑ ↓
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun View.layoutVertical(y: Int, fromBottom: Boolean = false) = layout(
        x = (this@CustomLayout.measuredWidth - measuredWidth) / 2,
        y = y,
        fromBottom = fromBottom
    )

    /**
     * 与目标View横向居中(处于targetView 的水平方向的中间)，上下移动 ↑ ↓
     * @param target 目标View
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun View.layoutVertical(
        y: Int, target: View, fromBottom: Boolean = false
    ) = layout(
        x = target.left + (target.measuredWidth - measuredWidth) / 2,
        y = y,
        fromBottom = fromBottom
    )


    @Suppress("NOTHING_TO_INLINE")
    protected inline fun View.layoutVertical(
        target: View, fromBottom: Boolean = false
    ) = layoutVertical(target.bottom,target, fromBottom)


    /**
     * 多view在垂直居中 横向排列 →
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun layoutHorizontal(vararg views: View): Int {
        return layoutHorizontal(0, *views)
    }

    /**
     * 多view垂直居中 横向排列 →
     * @param startX x轴起点
     */
    protected fun layoutHorizontal(startX: Int, vararg views: View): Int {
        var leftX = startX
        for (view in views) {
            leftX += view.leftMargin
            view.layoutHorizontal(leftX)
            leftX += view.measuredWidth + view.rightMargin
        }
        return leftX
    }

    /**
     * 多view垂直固定 横向排列 →
     * @param startX x轴起点
     * @param centerY y轴中点
     */
    protected fun layoutHorizontal(startX: Int, centerY: Int, vararg views: View): Int {
        var leftX = startX
        for (view in views) {
            leftX += view.leftMargin
            view.layout(leftX, centerY - view.measuredHeight / 2)
            leftX += view.measuredWidth + view.rightMargin
        }
        return leftX
    }

    /**
     * 多view在垂直方向排列
     */
    protected fun layoutVertical(vararg views: View): Int {
        return layoutVertical(0, *views)
    }

    /**
     * 多view在垂直方向排列
     * @param startY y轴起点
     */
    protected fun layoutVertical(startY: Int, vararg views: View): Int {
        var topY = startY
        for (view in views) {
            topY += view.topMargin
            view.layoutVertical(topY)
            topY += view.measuredHeight + view.bottomMargin
        }
        return topY
    }

    /**
     * 多view横向居中 横向排列 ↓
     * @param startY y轴起点
     * @param centerX x轴中点
     */
    protected fun layoutVertical(startY: Int, centerX: Int, vararg views: View): Int {
        var topY = startY
        for (view in views) {
            topY += view.topMargin
            view.layout(centerX - view.measuredWidth / 2, topY)
            topY += view.measuredHeight + view.bottomMargin
        }
        return topY
    }

    /**
     * 与目标View横向居中
     * @param target 目标View
     * @return x轴起点
     */
    protected fun View.horizontalCenterOf(target: View): Int {
        return target.left + (target.measuredWidth - measuredWidth) / 2
    }

    /**
     * 返回与目标View垂直居中 的 Y 坐标
     * @param target 目标View
     * @return y轴起点
     */
    protected fun View.verticalCenterOf(target: View): Int {
        return target.top + (target.measuredHeight - measuredHeight) / 2
    }



    protected fun plusWidthWithMargins(vararg views: View): Int {
        return views.sumOf { it.measureWidthWithMargins }
    }

    protected fun plusHeightWithMargins(vararg views: View): Int {
        4 * 3
        return views.sumOf { it.measureHeightWithMargins }
    }

    val Int.dp:Int get() = ( this * (Resources.getSystem().displayMetrics.density + 0.5f)  ).toInt()


}

