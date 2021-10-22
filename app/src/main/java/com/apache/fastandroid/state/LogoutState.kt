package com.apache.fastandroid.state

import android.content.Context
import com.blankj.utilcode.util.ToastUtils
import com.wjx.android.wanandroidmvvm.common.state.callback.CollectListener

/**
 * Created by Jerry on 2021/10/14.
 */
class LogoutState:UserState {
    override fun collect(context: Context, id: Int, listener: CollectListener) {
//        startLoginActivity(context)
        listener.collect(id)
    }

    private fun startLoginActivity(context: Context){
        context?.let {
            ToastUtils.showShort("请先登录")
//           startActivity<LoginActivity>(context)
        }
    }
}