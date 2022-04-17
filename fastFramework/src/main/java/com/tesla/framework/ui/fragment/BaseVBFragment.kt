package com.tesla.framework.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.viewbinding.ViewBinding
import com.tesla.framework.ui.activity.BaseVmActivityNew


/**
 * Fragment基类
 */

abstract class BaseVBFragment<T : ViewBinding>(val inflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> T) : ABaseFragment() {

    protected lateinit var mBinding: T
    val TAG = "BaseVBFragment"


    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mBinding = inflater(inflater, container, false)

        bindUI(mBinding.root)


        return mBinding.root
    }

    fun toolbarTitle():String?{
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarTitle()?.let {
            val base = activity as BaseVmActivityNew<T>
            base.setToolbarTitle(it)
        }
        layoutInit(layoutInflater,savedInstanceState)
    }

    open fun bindUI(rootView: View?) {}

    open fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {}




    open fun getRootView(): View {
        return mBinding.root
    }

    open fun <T : View?> findViewById(@IdRes resId: Int): T {
        return getRootView().findViewById(resId)
    }




}