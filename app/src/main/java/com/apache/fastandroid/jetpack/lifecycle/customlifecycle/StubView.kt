package com.apache.fastandroid.jetpack.lifecycle.customlifecycle

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/11/22.
 */
class StubView(context: Context?) : LinearLayout(context) {


    fun release() {

        Logger.d("StubView", "release")

    }



    init {

        val tv = TextView(context).apply {

            text= "占位控件"

            gravity = Gravity.CENTER

            setBackgroundColor(Color.BLUE)

            setTextColor(Color.WHITE)

        }

        this.addView(tv)

    }

}

