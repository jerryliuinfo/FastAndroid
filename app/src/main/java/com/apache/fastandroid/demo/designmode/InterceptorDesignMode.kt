package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCommonBinding
import com.tesla.framework.performance.watchdog.ANRWatchDog
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/9/24.
 */
class InterceptorDesignMode:BaseBindingFragment<FragmentCommonBinding>(FragmentCommonBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        //如果ANRInterceptor 中  intercept 返回的值 大于0，将不会执行下面的 onAppNotResponding
        ANRWatchDog(2000).setANRInterceptor {
            return@setANRInterceptor (5000 - it)
        }.start()
    }



}