package com.apache.fastandroid.demo.basic

import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 * Created by Jerry on 2021/3/5.
 * 默认规则
 */
class LaunchModeFragment: BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.android_basic_launch_mode
    }
}