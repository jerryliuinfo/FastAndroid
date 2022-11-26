package com.tesla.framework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.tesla.framework.kt.inflateBinding


/**
 * Fragment基类
 */
abstract class BaseDBFragment<T : ViewDataBinding>(val inflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> T) : BaseFragment() {

    protected lateinit var mBinding: T

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = inflateBinding<T>(layoutInflater).apply {
            lifecycleOwner = this@BaseDBFragment
        }
        return mBinding.root
    }




}