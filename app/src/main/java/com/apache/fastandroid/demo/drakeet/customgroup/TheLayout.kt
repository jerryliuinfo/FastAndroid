package com.apache.fastandroid.demo.drakeet.customgroup

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.marginEnd
import androidx.core.view.marginRight
import com.apache.fastandroid.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.seiko.demo.base.CustomLayout
import com.tesla.framework.kt.dpInt

/**
 * Created by Jerry on 2023/10/15.
 * measureWidth 是经过了 onMeasure之后得到的测量值
 * width 是经过了 layout 之后的值，
 * 通常这两者的值是一致的，因为不会切割，但 onDraw中建议用 widht，因为是最终的值
 */
class TheLayout(context: Context, attrs: AttributeSet? = null) : CustomLayout(context, attrs) {
    val header = AppCompatImageView(context).apply {
        scaleType = ImageView.ScaleType.CENTER_CROP
        setImageResource(R.drawable.profile)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 280.dpInt)
        addView(this)
    }



    val fab = FloatingActionButton(context).autoAddView(){
        setImageResource(R.drawable.ic_headset)
        setMargins(right = 20.dp)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        header.autoMeasure()
        fab.autoMeasure()
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        header.layout(0,0)
        fab.let {
//            it.layout(x = it.marginRight, y = header.bottom - (it.measuredHeight /2), fromRight =true)
            it.layout(x = it.marginRight, y = header.bottom - (it.measuredHeight /2), fromRight =true)
        }
    }
}