package com.tesla.framework.component.dialogchain

import androidx.annotation.CallSuper
import com.csd.dialogchain.DialogChain
import com.csd.dialogchain.IDialogInterceptor

/**

 * Author：岑胜德 on 2021/11/22 00:23

 * 说明：

 */
class DialogInterceptor : IDialogInterceptor {
    private var mChain: DialogChain? = null

    @CallSuper
    override fun intercept(chain: DialogChain) {
        mChain = chain
    }


    /*执行下一个拦截器*/
    fun chain(): DialogChain? = mChain

}