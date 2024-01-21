package com.apache.fastandroid.demo.guide.appresource

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2023/12/15.
 * 存在一个已知问题：在某些情况下，在 Android 12L（API 级别 32）和 Android 13（API 级别 33）的早期版本中可能无法调用 View.onConfigurationChanged()。
 * 如需了解详情，请参阅此公开问题。此问题在较新的 Android 13 版本和 Android 14 中已得到解决。
 */
class ConfigChangeTextView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        Logger.d("ConfigChangeTextView onConfigurationChanged--->")
    }
}