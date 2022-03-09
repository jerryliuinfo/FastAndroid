package com.tesla.framework.ui.fragment

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.IllegalStateException
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType


/**
 * Fragment基类
 */
abstract class BaseVBFragment<T : ViewBinding>(val inflater: (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> T) : ABaseFragment() {



    protected lateinit var viewBinding: T

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        viewBinding = inflater(inflater, container, false)

        bindUI(viewBinding.root)


        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutInit(layoutInflater,savedInstanceState)
    }

    open fun bindUI(rootView: View?) {}

    open fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {}




    open fun getRootView(): View {
        return viewBinding.root
    }

    open fun <T : View?> findViewById(@IdRes resId: Int): T {
        return getRootView().findViewById(resId)
    }




}