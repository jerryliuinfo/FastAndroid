package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import androidx.core.view.ViewCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.temp_api_usage_demo.*
import kotlinx.android.synthetic.main.temp_reflection.*

/**
 * Created by Jerry on 2021/10/27.
 */
class ApiDemoFragment:BaseFragment() {
    companion object{
        private  const val TAG = "ApiDemoFragment"
    }

    override fun inflateContentView(): Int {
        return R.layout.temp_api_usage_demo
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        tv_get_scaled_touch_slop.setOnClickListener {
            scaleTouchSlop()
        }

        tv_get_LayoutDirection.setOnClickListener {
            getLayoutDirection()
        }


    }

    fun scaleTouchSlop(){
        var scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        NLog.d(TAG, "scaledTouchSlop value:${scaledTouchSlop}")
    }

    fun getLayoutDirection(){
        var layoutDirection = ViewCompat.getLayoutDirection(liner1)
        NLog.d(TAG, "layoutDirection value:${layoutDirection}")
    }


}