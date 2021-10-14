package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.github.anrwatchdog.ANRWatchDog
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/9/24.
 */
class InterceptorDesignMode:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_common
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        //如果ANRInterceptor 中  intercept 返回的值 大于0，将不会执行下面的 onAppNotResponding
        ANRWatchDog(2000).setANRInterceptor {
            return@setANRInterceptor (5000 - it)
        }.start()
    }



}