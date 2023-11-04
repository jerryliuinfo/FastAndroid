package com.apache.fastandroid.demo.drakeet.customview.sample.course

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.apache.fastandroid.R
import com.seiko.demo.base.CustomLayout
import com.seiko.demo.base.CustomLayoutExtensions.Companion.INVALID_VIEW_SIZE
import com.tesla.framework.component.imageloader.ImageLoaderManager

class CameraUserLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CustomLayout(context, attrs) {

    @JvmField
    val camera = FrameLayout(context).autoAddViewMax()

    @JvmField
    val avatar = ImageView(context).autoAddViewMax() {
        scaleType = ImageView.ScaleType.CENTER_CROP
    }

    @JvmField
    val name = TextView(context).autoAddView(height = 30.dp) {
        setTextColor(Color.WHITE)
        setTextSizePx(15.sp)
        setPadding(10.dp, 0, 10.dp, 0)
        ellipsize = TextUtils.TruncateAt.END
        maxLines = 1
        gravity = Gravity.CENTER
        it.topMargin = 5.dp
        it.leftMargin = 5.dp
    }

    @JvmField
    val networkQuality = ImageView(context).autoAddView(12.dp) {
        it.leftMargin = 5.dp
        it.bottomMargin = 5.dp
        ImageLoaderManager.load(this, R.mipmap.ic_class_net_good)
    }

//    init {
//        ImageLoaderManager.load(networkQuality, R.mipmap.ic_class_net_good)
//    }

    fun setNameViewHeightAndTextSize(height: Int = INVALID_VIEW_SIZE, textSize: Float) {
        name.setTextSizePx(textSize)
        name.setViewSize(height = height)
        if (height != INVALID_VIEW_SIZE) {
            ImageLoaderManager.load(name, "#82000000", height / 2)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        autoMeasure(camera, avatar, name, networkQuality)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        camera.layout(0, 0)
        avatar.layout(0, 0)
        name.let { it.layout(it.leftMargin, it.topMargin) }
        networkQuality.let { it.layout(it.leftMargin, it.bottomMargin, fromBottom = true) }
    }

    private val roundPath = Path()
    private val round = 6.dp.toFloat()

    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.let {
            roundPath.reset()
            val rectF = RectF(
                paddingLeft.toFloat(),
                paddingTop.toFloat(),
                (measuredWidth - paddingRight).toFloat(),
                (measuredHeight - paddingBottom).toFloat()
            )
            roundPath.addRoundRect(rectF, round, round, Path.Direction.CW)
            it.clipPath(roundPath)

        }
        super.dispatchDraw(canvas)
    }
}