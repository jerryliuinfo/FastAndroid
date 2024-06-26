package com.apache.fastandroid.demo.viewbinding

import android.view.View
import com.apache.fastandroid.databinding.ActivityMainNewBinding
import com.tesla.framework.component.viewbinding.viewBinding
import com.tesla.framework.ui.activity.BaseActivity

/**
 * Created by Jerry on 2023/9/29.
 */
class ViewBindingDemoActivity:BaseActivity() {

    private val mBinding  by viewBinding(ActivityMainNewBinding::inflate)

    override fun inflateView(): View {
        return mBinding.root
    }
}