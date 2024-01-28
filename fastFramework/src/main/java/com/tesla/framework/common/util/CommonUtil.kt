package com.tesla.framework.common.util

import android.app.PendingIntent
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.annotation.ChecksSdkIntAtLeast
import com.tesla.framework.component.logger.Logger
import java.lang.reflect.ParameterizedType


/**
 * Created by Jerry on 2021/11/10.
 */
object CommonUtil {
    const val ANROID_O = 31

    fun <T> getClass(t:Any):Class<T>{
        return (t.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<T>
    }

    /**
     * 源自:https://t.zsxq.com/Nvzjyn2
     * 解决三星 Android 9 及以上手机，通知栏消息PendingIntent中如果带有ParcelableExtra对象，会导致当该消息被点击后，接收该Intent的代码拿不到原本放入其中的所有
     * extra 数据，包括那个 Parcelable 对象
     */
    fun getFlagsCompat(flags:Int):Int{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
            && "samsung".equals(Build.MANUFACTURER,ignoreCase = true)){
                flags or PendingIntent.FLAG_IMMUTABLE
        }else{
            flags
        }
    }


    /**
     * 源自:https://t.zsxq.com/nuJqFY3
     * 帮助lint识别间接的 DK_INT 检查，在此之前lint有时很愚蠢，它大多数情况下只能识别
     * 最直接的写法，比如 Build.VERSION.SDK_INT >= Build.VERSION_CODES.O， 但无法识别间接封装的条件，
     * 即便内容是一样的，这引起的麻烦是我们不得不重复手写  Build.VERSION.SDK_INT >= Build.VERSION_CODES.O 代码
     *
     */
   @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    fun isAtLeastO(): Boolean {
        //26
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    // Required API level is passed in as first argument, and function
    // in second parameter is executed if SDK_INT is at least that high:
//    @ChecksSdkIntAtLeast(parameter = 0, lambda = 1)
    inline fun fromApi(value: Int, action: () -> Unit) {
        if (Build.VERSION.SDK_INT >= value) {
            action()
        }
    }




    // Kotlin property:
//    @get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.GINGERBREAD)
    val isGingerbread: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD


    /**
     * 判断用户是否在取样范围内
     * @param appInstallID String
     * @param sampleRate Int
     * @return Boolean
     */
    fun isUserInSamplingGroup(appInstallID: String, sampleRate: Int): Boolean {
        return try {
            val lastFourDigits = appInstallID.substring(appInstallID.length - 4).toInt(16)
            lastFourDigits % sampleRate == 0
        } catch (e: Exception) {
            // Should never happen, but don't crash just in case.
            Logger.e(e.message ?: "")
            false
        }
    }


    /**
     * 避免中途断电文件无法写入
     */
    fun syncWrite(){
        try {
            Runtime.getRuntime().exec("sync")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun fromHtml(input: String): Spanned {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Html.fromHtml(input, Html.FROM_HTML_MODE_COMPACT)
        } else {
            // method deprecated at API 24.
            @Suppress("DEPRECATION")
            Html.fromHtml(input)
        }
    }

}