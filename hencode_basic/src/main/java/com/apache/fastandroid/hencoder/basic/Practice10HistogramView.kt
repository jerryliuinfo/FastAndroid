package com.apache.fastandroid.hencoder.basic

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.common.util.kt.dp

/**
 * Created by Jerry on 2020/11/12.
 */
class Practice10HistogramView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1.dp
        color = Color.WHITE
    }

    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 32f
        color = Color.WHITE
    }

    private val mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GREEN
    }

    private val mRect = RectF()

    private val mPath = Path()

    private var mCordinateLineY: Float = 0f


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        //x轴的 Y坐标
        mCordinateLineY = height - 2 * MARGIN_BOTTOM
    }

    private val mDatas = mutableListOf(
            PieBean("Freyo", 1),
            PieBean("GB", 10),
            PieBean("ICS", 10),
            PieBean("JB", 50),
            PieBean("KitKat", 80),
            PieBean("L", 100),
            PieBean("M", 40)
    )

    companion object {
        private const val TAG = "Practice10HistogramView"
        private val MARGIN_BOTTOM = 40.dp

        //每一个方柱的宽度
        private val RECT_WIDTH = 30.dp

        //每个柱形条的间隔
        private val RECT_GAP = RECT_WIDTH / 4
        //文字和柱形底部距离
        private val TEXT_RECT_GAP = 2.dp

        //X轴 和 Y轴 预留宽度 （不用来画柱形条 ）
        private val RESERVE_WIDTH = RECT_WIDTH / 2

        private const val TEXT = "直方图"
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPath.reset()
        //画坐标系
        mPath.lineTo(0f, mCordinateLineY)

        mPath.lineTo(width.toFloat(), mCordinateLineY)
        canvas.drawPath(mPath, mLinePaint)

        val bounds = Rect()
        mLinePaint.getTextBounds(TEXT, 0, TEXT.length, bounds)
        //画 「直方图」文字
        canvas.drawText(TEXT, width / 2 - bounds.width() / 2.toFloat(), height - MARGIN_BOTTOM / 2 , mTextPaint)


        for ((index, item) in mDatas.withIndex()) {
            val left = index * RECT_WIDTH + RECT_GAP * (index + 1)
            val right = left + RECT_WIDTH
            mRect.set(left, (1 - item.value / 100f) * (mCordinateLineY - RESERVE_WIDTH), right, mCordinateLineY)
            //画柱状图
            canvas.drawRect(mRect, mRectPaint)


            mTextPaint.getTextBounds(item.name, 0, item.name.length, bounds)
            //画柱状图 下方文字
            val textX = (left + right) / 2 - bounds.width() / 2
            val textY = mCordinateLineY +  TEXT_RECT_GAP + bounds.height()
            canvas.drawText(item.name, textX, textY, mTextPaint  )
        }
        canvas.drawLine(0f,height.toFloat(),width.toFloat(),height.toFloat(), mRectPaint)
    }

    private data class PieBean(val name: String, val value: Int)
}
