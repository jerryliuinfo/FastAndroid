package com.apache.fastandroid.demo.drakeet.customview.sample.course

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import com.apache.fastandroid.R
import com.seiko.demo.base.CustomLayout
import com.seiko.demo.course.CourseType
import com.tesla.framework.component.imageloader.ImageLoaderManager
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.dp

class OnlineActivityLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : CustomLayout(context, attrs) {

    @JvmField
    val topLayout = OnlineTopLayout(context).autoAddViewMax(height = 37.dp)

    @JvmField
    val teachingLayout = OnlineTeachingLayout(context).autoAddViewMax(height = 338.dp)

    private val btnChange = TextView(context).autoAddView(40.dp) {
        setBackgroundColor(Color.BLUE)
        setTextColor(Color.WHITE)
        gravity = Gravity.CENTER
        text = "S"
    }

    init {
        ImageLoaderManager.load(this, R.mipmap.bg_attend_class)

        val courseTypes = arrayOf(
            CourseType.Stage,
            CourseType.WhiteBoard,
            CourseType.Musical,
            CourseType.CourseWare
        )
        var index = 1
        btnChange.setOnClickListener {
            teachingLayout.courseType = courseTypes[index++]
            btnChange.text = teachingLayout.courseType.name
            if (index >= 4) index = 0
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Logger.d("OnlineActivityLayout onMeasure")

        topLayout.autoMeasure()
        teachingLayout.autoMeasure()
        btnChange.autoMeasure()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Logger.d("OnlineActivityLayout onLayout")

        layoutVertical(topLayout, teachingLayout)
        btnChange.layout(0, 0, fromBottom = true)
    }
}