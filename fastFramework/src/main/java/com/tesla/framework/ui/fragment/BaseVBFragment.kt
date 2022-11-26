package com.tesla.framework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding


/**
 * Fragment基类
 */

abstract class BaseVBFragment<T : ViewBinding>(val inflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> T) : BaseFragment() {

    protected lateinit var mBinding: T

    /*@CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = inflater(inflater, container, false)
        bindUI(mBinding.root)

        return mBinding.root
    }*/

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = inflater(inflater, container, false)
        return mBinding.root
    }


    companion object{
        val TAG = "BaseVBFragment"
    }

}