package com.apache.fastandroid.demo.component.dialogchanin

import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import com.csd.dialogchain.DialogChain
import com.csd.dialogchain.IDialogInterceptor

/**

 * Author：岑胜德 on 2021/11/26 21:42

 * 说明：

 */
abstract class BaseDialog(context: Context):AlertDialog(context), IDialogInterceptor {
    private var mChain: DialogChain? = null

    @CallSuper
    override fun intercept(chain: DialogChain) {
        mChain = chain
    }


    /*执行下一个拦截器*/
    fun chain(): DialogChain? = mChain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.attributes?.width=800
        window?.attributes?.height=900


    }


}