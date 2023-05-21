package com.seiko.demo.course

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.apache.fastandroid.R
import com.seiko.demo.base.CustomLayout
import com.tesla.framework.common.util.DrawableUtils
import com.tesla.framework.component.imageloader.ImageLoaderManager

class WhiteBoardUtilLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CustomLayout(context, attrs) {

    var isShowPen = false
        private set(value) {
            if (field != value) {
                field = value
                post { changeChildViewState() }
                requestLayout()
            }
        }

    private val btnSize = 27.5f.dp
    private val btnMargin = 20.5f.dp
    private val btnPenSize = 24.dp
    private val lineWidth = 0.5f.dp

    @JvmField
    val btnShowPen = ImageView(context).autoAddView(btnSize)

    @JvmField
    val btnRetreat = createBtn(context)

    @JvmField
    val btnClear = createBtn(context)

    @JvmField
    val penColorRed = createPenColorBtn(context)

    @JvmField
    val penColorGreen = createPenColorBtn(context)

    @JvmField
    val penColorOrange = createPenColorBtn(context)

    @JvmField
    val penColorBlue = createPenColorBtn(context)

    @JvmField
    val penColorBlack = createPenColorBtn(context)

    @JvmField
    val pen0 = createPenBtn(context, 10.dp)

    @JvmField
    val pen2 = createPenBtn(context, 15.dp)

    @JvmField
    val pen4 = createPenBtn(context, 20.dp)

    private val lineShowPen = createLine(context)
    private val lineRetreat = createLine(context)
    private val lineClear = createLine(context)
    private val linePenColor = createLine(context)

    // ↓ 以下View的间距为动态算出
    private val toolViewsWithOutBtn = arrayOf(
        pen0, pen2, pen4, linePenColor,
        penColorRed, penColorOrange, penColorGreen, penColorBlue, penColorBlack, lineClear
    )

    private val toolViews = toolViewsWithOutBtn + arrayOf(
        btnClear, lineRetreat,
        btnRetreat, lineShowPen
    )

    init {
        background = DrawableUtils.createRadiusDrawable(
            Color.parseColor("#FFDC6B"),
            topLeft = 6.dp,
            bottomLeft = 6.dp
        )
        ImageLoaderManager.load(btnShowPen, R.mipmap.whiteboard_pen)
        ImageLoaderManager.load(btnRetreat, R.mipmap.whiteboard_retreat)
        ImageLoaderManager.load(btnClear, R.mipmap.whiteboard_clear)
        ImageLoaderManager.load(penColorRed, R.drawable.select_board_red)
        ImageLoaderManager.load(penColorGreen, R.drawable.select_board_green)
        ImageLoaderManager.load(penColorOrange, R.drawable.select_board_orange)
        ImageLoaderManager.load(penColorBlue, R.drawable.select_board_blue)
        ImageLoaderManager.load(penColorBlack, R.drawable.select_board_black)
        ImageLoaderManager.load(pen0, R.drawable.select_boardpen1)
        ImageLoaderManager.load(pen2, R.drawable.select_boardpen2)
        ImageLoaderManager.load(pen4, R.drawable.select_boardpen4)

        pen0.isSelected = true
        penColorBlack.isSelected = true

        btnShowPen.setOnClickListener {
            isShowPen = !isShowPen
        }
    }

    private fun changeChildViewState() {
        lineShowPen.isVisible = isShowPen
        lineRetreat.isVisible = isShowPen
        lineClear.isVisible = isShowPen
        linePenColor.isVisible = isShowPen
        btnRetreat.isVisible = isShowPen
        btnClear.isVisible = isShowPen
        penColorRed.isVisible = isShowPen
        penColorGreen.isVisible = isShowPen
        penColorOrange.isVisible = isShowPen
        penColorBlue.isVisible = isShowPen
        penColorBlack.isVisible = isShowPen
        pen0.isVisible = isShowPen
        pen2.isVisible = isShowPen
        pen4.isVisible = isShowPen
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        btnShowPen.autoMeasure()
        if (!isShowPen) {
            return
        }

        autoMeasure(*toolViews)

        // 算出pen之间的间距
        val whiteBoardUtilWidth = MeasureSpec.getSize(widthMeasureSpec)
        val penPadding = (whiteBoardUtilWidth
                - 50.dp - btnMargin * 4 - btnPenSize * 10 - lineWidth * 4) / 10
        toolViewsWithOutBtn.setMargins(left = penPadding)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        btnShowPen.layoutHorizontal(10.dp, true)
        if (!isShowPen) {
            return
        }
        layoutHorizontal(*toolViews)
    }

    private fun createBtn(context: Context) =
        ImageView(context).autoAddView(btnSize) {
            it.leftMargin = btnMargin
            it.rightMargin = btnMargin
        }

    private fun createPenBtn(context: Context, size: Int) =
        ImageView(context).autoAddView(btnPenSize) {
            setPadding((btnPenSize - size) / 2)
        }

    private fun createPenColorBtn(context: Context) =
        ImageView(context).autoAddView(btnPenSize)

    private fun createLine(context: Context) =
        View(context).autoAddView(lineWidth, 30.dp) {
            setBackgroundColor(Color.WHITE)
        }
}