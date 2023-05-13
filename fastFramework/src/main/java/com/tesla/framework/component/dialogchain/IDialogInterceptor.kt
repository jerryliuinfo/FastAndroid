package com.csd.dialogchain

/**

 * Author：岑胜德 on 2021/11/21 23:40

 * 说明：

 */
interface IDialogInterceptor {

    fun intercept(chain: DialogChain)
}