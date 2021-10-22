package com.apache.fastandroid.state

import android.content.Context
import com.tesla.framework.kt.SPreference
import com.wjx.android.wanandroidmvvm.common.state.callback.CollectListener

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/03/01
 * Time: 19:30
 */
class UserInfo private constructor() {



    private var isLogin: Boolean by SPreference(LOGIN_KEY, false)

    // 设置默认状态
    var mState: UserState = if (isLogin) LoginState() else LogoutState()

    companion object {
        @JvmStatic
        val instance =
            Holder.INSTANCE
        const val USERNAME_KEY = "username"
        const val USERID_KEY = "userid"
        const val LOGIN_KEY = "login"
    }

    // 内部类 单利
    private object Holder {
        val INSTANCE = UserInfo()
    }

    // 收藏
    fun collect(context: Context, id: Int, listener: CollectListener) {
        mState.collect(context, id, listener)
    }


    fun loginSuccess(username: String, userId: String, collectIds: List<Int>?) {
        // 改变 sharedPreferences   isLogin值
        isLogin = true
        instance.mState = LoginState()

        // 登录成功 回调 -> DrawerLayout -> 个人信息更新状态
//        LoginSuccessState.notifyLoginState(username, userId, collectIds)
    }

    fun logoutSuccess() {
        instance.mState = LogoutState()
        // 清除 cookie、登录缓存
        var mCookie by SPreference("cookie", "")
        mCookie = ""
        isLogin = false
//        LoginSuccessState.notifyLoginState("未登录", "--", null)
    }
}