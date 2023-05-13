package com.csd.dialogchain

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tesla.framework.component.logger.Logger
import java.util.ArrayList

/**

 * Author：岑胜德 on 2021/11/21 23:38

 * 说明：

 */
open class DialogChain private constructor(
    val activity: FragmentActivity? = null,
    val fragment: Fragment? = null,
    private var interceptors: MutableList<IDialogInterceptor>?
) {
    companion object {
        @JvmStatic
        fun create(initialCapacity: Int = 0): Builder {
            return Builder(initialCapacity)
        }
        @JvmStatic
        fun openLog(isOpen:Boolean){
        }
    }

    private var index: Int = 0

    fun process() {
        interceptors ?: return
        when (index) {
            in interceptors!!.indices -> {
                val interceptor = interceptors!![index]
                index++
                interceptor.intercept(this)
            }
            interceptors!!.size -> {
                Logger.d("clearAllInterceptors")
                clearAllInterceptors()
            }
        }
    }

    private fun clearAllInterceptors() {
        interceptors?.clear()
        interceptors = null
    }

    open class Builder(private val initialCapacity: Int = 0) {
        private val interceptors by lazy(LazyThreadSafetyMode.NONE) {
            ArrayList<IDialogInterceptor>(
                initialCapacity
            )
        }
        private var activity: FragmentActivity? = null
        private var fragment: Fragment? = null


        fun addInterceptor(interceptor: IDialogInterceptor): Builder {
            if (!interceptors.contains(interceptor)) {
                interceptors.add(interceptor)
            }
            return this
        }

        fun attach(fragment: Fragment): Builder {
            this.fragment = fragment
            return this
        }

        fun attach(activity: FragmentActivity): Builder {
            this.activity = activity
            return this
        }


        fun build(): DialogChain {
            return DialogChain(activity, fragment, interceptors)
        }
    }


}