package com.apache.fastandroid.state

import android.content.Context
import com.wjx.android.wanandroidmvvm.common.state.callback.CollectListener

/**
 * Created by Jerry on 2021/10/14.
 */
interface UserState {
    fun collect(context:Context, id:Int, listener:CollectListener)
}