package com.apache.fastandroid.demo.drakeet.customview.sample.profile

import android.content.Context
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.apache.fastandroid.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.seiko.demo.base.CustomLayout
import com.tesla.framework.kt.getColor
import com.tesla.framework.kt.sp

/**
 * Created by Jerry on 2023/10/15.
 * measureWidth 是经过了 onMeasure之后得到的测量值
 * width 是经过了 layout 之后的值，
 * 通常这两者的值是一致的，因为不会切割，但 onDraw中建议用 widht，因为是最终的值
 */
class PersonInfoLayout(context: Context) :
    CustomLayout(context) {

    val margin = 24.dp

    val header = AppCompatImageView(context).apply {
        scaleType = ImageView.ScaleType.CENTER_CROP
        setImageResource(R.drawable.profile)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 280.dp)
        addView(this)
    }

    val fab = FloatingActionButton(context).autoAddView() {
        setImageResource(R.drawable.ic_headset)
        // 用 setMargin 无效,setMargin 中设置的 是  leftMargin和 rightMargin, 所以 layout的时候也必须用rightMargin
        it.rightMargin = 12.dp
    }

    // 头像
    val avator = ImageView(context).autoAddView() {
        setImageResource(R.mipmap.ic_launcher)
        it.leftMargin = margin
        it.topMargin = margin
    }

    val reply = ImageView(context).autoAddView() {
        setImageResource(R.drawable.ic_baseline_send_24)
        it.rightMargin = margin
        layoutParams = ViewGroup.LayoutParams(5.dp, 5.dp)
    }

    // 名字
    val itemTitle = TextView(context).autoAddView() {
        text = "Jerry"
        textSize = 10.sp
        it.leftMargin = 12.dp
        it.rightMargin = 12.dp
        setTextColor(R.color.red.getColor(context))
    }

    // 描述
    val itemMessage = TextView(context).autoAddView() {
        text =
            "This is a long Message This is a long Message  This is a long Message  This is a long Message"
        textSize = 10.sp
        ellipsize = TextUtils.TruncateAt.MARQUEE
        it.leftMargin = 12.dp
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 先测量其他几个 View
        autoMeasure(header, fab, avator, reply)

        val descItemWidth =
            measuredWidth - reply.measureWidthWithMargins - avator.measureWidthWithMargins
        -itemTitle.marginLeft - itemTitle.marginRight

        // 计算 title 和 message 的最大宽度
        itemTitle.measure(
            descItemWidth.toExactlyMeasureSpec(),
            itemTitle.defaultHeightMeasureSpec(this)
        )

        itemMessage.measure(
            descItemWidth.toExactlyMeasureSpec(),
            itemMessage.defaultHeightMeasureSpec(this)
        )

        // 计算 header 以下占据的最大高度
        val max =
            (avator.marginTop + itemTitle.measureHeightWithMargins + itemMessage.measureHeightWithMargins)
                .coerceAtLeast(avator.measureHeightWithMargins)
        // header 高度 + header 以下部分的高度，则是 ViewGroup 的最大高度
        val wrapContentHeight = header.measureHeightWithMargins + max


        // setMeasuredDimension(measuredWidth,wrapContentHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        header.layout(0, 0)

        fab.let {
            // fab 位于 header 底部的中间
            it.layout(
                x = it.marginRight,
                y = header.bottom - (it.measuredHeight / 2),
                fromRight = true
            )
        }
        // avator 位于 header 的左下方
        avator.let {
            it.layout(x = it.marginLeft, y = header.bottom + it.marginTop)
        }
        // profile 位于 avator 右边，顶部 和 avator 对齐
        itemTitle.let {
            it.layout(x = avator.right + it.leftMargin, y = avator.top + it.marginTop)
        }

        itemMessage.let {
            it.layout(x = avator.right + it.leftMargin, y = itemTitle.bottom + it.marginTop)
        }

        reply.let {
            it.layout(x = it.marginRight, y = avator.top + it.marginTop, fromRight = true)
        }


    }
}