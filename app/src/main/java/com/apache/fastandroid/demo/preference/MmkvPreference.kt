package com.apache.fastandroid.demo.preference

import com.apache.fastandroid.demo.preference.IDataSource.IGroup1Preferences.Companion.KEY_FOLLOW_REDIRECT
import com.apache.fastandroid.demo.preference.IDataSource.IGroup1Preferences.Companion.KEY_HAS_CONSENT_STATUS
import com.tencent.mmkv.MMKV

/**
 * Created by Jerry on 2024/5/19.
 */
class MmkvPreference(private val mmkv: MMKV):IDataSource.IGroup1Preferences {
    override fun isFollowRedirect(): Boolean {
        return mmkv.getBoolean(KEY_FOLLOW_REDIRECT,true)
    }

    override fun hasConsentStatus(): Boolean {
        return mmkv.getBoolean(KEY_HAS_CONSENT_STATUS,true)
    }

    override fun updateNightMode(value:Int) {

    }
}