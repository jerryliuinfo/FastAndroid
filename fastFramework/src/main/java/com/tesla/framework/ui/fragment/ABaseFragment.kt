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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.lang.IllegalStateException

/**
 * Created by Jerry on 2022/3/10.
 */
open class ABaseFragment:Fragment() {
    private var isFirstLoad = true
    protected lateinit var mActivity: ComponentActivity

    private var mFragmentProvider: ViewModelProvider? = null
    private var mActivityProvider: ViewModelProvider? = null
    private var mApplicationProvider: ViewModelProvider? = null

    private val compositeDisposable = CompositeDisposable()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = requireActivity()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstLoad) {
            isFirstLoad = false
            onLazyLoad()
        }
    }

    /**
     * Fragment 懒加载
     */
    protected open fun onLazyLoad() {}


    open fun <T : ViewModel> getFragmentScopeViewModel(modelClass: Class<T>): T {
        if (mFragmentProvider == null) {
            mFragmentProvider = ViewModelProvider(this)
        }
        return mFragmentProvider!!.get(modelClass)
    }

    open fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
        if (mActivityProvider == null) {
            mActivityProvider = ViewModelProvider(mActivity)
        }
        return mActivityProvider!!.get(modelClass)
    }

    /**
     * 全局共享的ViewModel,类似于单例，但是跟单例不同的是，这个ViewModel仅限于在Activity或者Fragment当中使用
     * @param modelClass
     * @param <T>
     * @return
    </T> */
    open fun <T : ViewModel?> getApplicationScopeViewModel(modelClass: Class<T>): T {
        if (mApplicationProvider == null) {
            mApplicationProvider = ViewModelProvider(
                (mActivity.getApplicationContext() as ViewModelStoreOwner),
                getApplicationFactory(mActivity)!!
            )
        }
        return mApplicationProvider!!.get(modelClass)
    }

    private  fun getApplicationFactory(activity: Activity): ViewModelProvider.Factory? {
        checkActivity(this)
        val application = checkApplication(activity)
        return ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    private  fun checkActivity(fragment: Fragment) {
        val activity = fragment.activity
            ?: throw IllegalStateException("Can't create ViewModelProvider for detached fragment")
    }


    private  fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }

    protected open fun initViewModel() {}



    @CallSuper
    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }

    /**
     * 添加Disposable
     */
     fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}