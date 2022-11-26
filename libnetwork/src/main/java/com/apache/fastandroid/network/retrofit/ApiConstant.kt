package com.apache.fastandroid.network.retrofit

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/24
 * Time: 17:36
 */
object ApiConstant {
    const val BASE_URL = "https://www.wanandroid.com"
    const val DISNEY_URL = "https://gist.githubusercontent.com/skydoves/aa3bbbf495b0fa91db8a9e89f34e4873/raw/a1a13d37027e8920412da5f00f6a89c5a3dbfb9a/"
    const val FLOW_BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"

    const val USERNAME_KEY = "username"
    const val USERID_KEY = "userid"
    const val LOGIN_KEY = "login"

    const val SAVE_USER_LOGIN_KEY = "user/login"
    const val SAVE_USER_REGISTER_KEY = "user/register"
    const val SET_COOKIE_KEY = "set-cookie"
    const val COOKIE_NAME = "Cookie"

    const val NIGHT_MODE = "night_mode"

    const val HOME = 0
    const val SYSTEM = 1
    const val WECHAT = 2
    const val NAVIGATION = 3
    const val PROJECT = 4

    const val SUCCESS = 0
    const val NOT_LOGIN = -1001

    const val ADD_TODO = 1.toString() + ""

    const val EDIT_TODO = 2.toString() + ""

    const val TODO_WORK = 1

    const val TODO_STUDY = 2

    const val KEY_TODO_TITLE = "todo_title"

    const val KEY_TODO_CONTENT = "todo_content"

    const val KEY_TODO_DATE = "todo_date"


    const val KEY_TODO_PRIORITY = "todo_priority"

    const val KEY_TODO_ID = "todo_id"

    const val KEY_TODO_TYPE = "todo_type"

    const val KEY_TODO_HANDLE_TYPE = "todo_handle"

    // 二维码扫描
    const val REQUEST_CODE_SCAN = 1

    const val CODED_CONTENT = "codedContent"

    const val INTENT_ZXING_CONFIG = "zxingConfig"

    // 应用更新
    const val UPDATEAPK = "update_apk"
}