package com.apache.fastandroid.util.constants

import com.apache.fastandroid.BuildConfig
import com.blankj.utilcode.util.SPUtils
import com.tencent.mmkv.MMKV

/**
 * Created by Jerry on 2022/5/10.
 */
object GlobalConstans {

    val spName = if (BuildConfig.DEBUG) "com.apache.fasntadnroid.debug" else "com.apache.fasntadnroid"

    val mmkv: MMKV =
        MMKV.mmkvWithID(spName) ?: throw IllegalStateException("mmkv instance is null")


    var cardMode
        get() = mmkv.decodeString(Const.PREF_CARD_MODE) ?: Const.PREF_CARD_MODE_MEDIUM
        set(value) {
            mmkv.encode(Const.PREF_CARD_MODE, value)
        }
}