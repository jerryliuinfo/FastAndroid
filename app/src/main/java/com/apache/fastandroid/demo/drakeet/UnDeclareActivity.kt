package com.apache.fastandroid.demo.drakeet

import android.os.Bundle
import com.apache.fastandroid.R
import com.tesla.framework.ui.activity.BaseActivity

/**
 * Created by Jerry on 2021/10/18.
 */
class UnDeclareActivity: BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.drakeet_looper_activity
    }

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

    }
}