package com.apache.fastandroid.demo.preference

import android.content.SharedPreferences
import com.apache.fastandroid.demo.preference.LocalDataSource.Preferences.Companion.KEY_CONSENT_STATUS
import com.apache.fastandroid.demo.preference.LocalDataSource.Preferences.Companion.KEY_FOLLOW_REDIRECT
import com.apache.fastandroid.demo.preference.LocalDataSource.Preferences.Companion.KEY_HAS_CONSENT_STATUS

class DefaultPreferences constructor(override val sharedPreferences: SharedPreferences)
    : LocalDataSource.Preferences {

    override fun isFollowRedirect(): Boolean =
        sharedPreferences.getBoolean(KEY_FOLLOW_REDIRECT, true)

    override fun getConsentStatus(): Boolean =
        sharedPreferences.getBoolean(KEY_CONSENT_STATUS, true)

    override fun hasConsentStatus(): Boolean =
        sharedPreferences.getBoolean(KEY_HAS_CONSENT_STATUS, false)

    override fun setConsentStatus(consentStatus: Boolean) =
        sharedPreferences.edit()
            .putBoolean(KEY_CONSENT_STATUS, consentStatus)
            .putBoolean(KEY_HAS_CONSENT_STATUS, true)
            .apply()
}
