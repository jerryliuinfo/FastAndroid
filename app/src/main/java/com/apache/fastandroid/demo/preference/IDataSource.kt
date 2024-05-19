package com.apache.fastandroid.demo.preference

/**
 * Created by Jerry on 2023/4/24.
 */
interface IDataSource {

    interface IGroup1Preferences : IDataSource {
        // val sharedPreferences:SharedPreferences

        fun isFollowRedirect(): Boolean


        fun hasConsentStatus(): Boolean


        fun updateNightMode(value:Int)

        companion object {
            const val KEY_FOLLOW_REDIRECT = "key_follow_redirect"
            const val KEY_HAS_CONSENT_STATUS = "key_has_consent_status"
        }
    }
}