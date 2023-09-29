package com.apache.fastandroid.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.apache.fastandroid.databinding.ViewTabsCountBinding

/**
 * Created by Jerry on 2023/9/28.
 */
class TabCountView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    private val binding = ViewTabsCountBinding.inflate(LayoutInflater.from(context), this)
}