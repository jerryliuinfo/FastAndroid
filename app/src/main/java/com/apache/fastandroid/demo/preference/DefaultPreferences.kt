package com.apache.fastandroid.demo.preference

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apache.fastandroid.demo.preference.IDataSource.IGroup1Preferences.Companion.KEY_FOLLOW_REDIRECT
import com.apache.fastandroid.demo.preference.IDataSource.IGroup1Preferences.Companion.KEY_HAS_CONSENT_STATUS

/**
 * SharedPreference 实现
 * @property sharedPreferences SharedPreferences
 * @constructor
 */
class DefaultPreferences constructor(private val sharedPreferences: SharedPreferences)
    : IDataSource.IGroup1Preferences {

    companion object{
        private const val PREFERENCE_NIGHT_MODE = "preference_night_mode"
        private const val PREFERENCE_NIGHT_MODE_DEF_VAL = AppCompatDelegate.MODE_NIGHT_NO

    }

    private val nightMode: Int
        get() = sharedPreferences.getInt(PREFERENCE_NIGHT_MODE, PREFERENCE_NIGHT_MODE_DEF_VAL)

    private val _nightModeLive: MutableLiveData<Int> = MutableLiveData()
    val nightModeLive: LiveData<Int>
        get() = _nightModeLive


    var isDarkTheme: Boolean = false
        get() = nightMode == AppCompatDelegate.MODE_NIGHT_YES
        set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_NIGHT_MODE, if (value) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }).apply()
            field = value
        }

    private val _isDarkThemeLive: MutableLiveData<Boolean> = MutableLiveData()
    val isDarkThemeLive: LiveData<Boolean>
        get() = _isDarkThemeLive

    private val preferenceChangedListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                PREFERENCE_NIGHT_MODE -> {
                    _nightModeLive.value = nightMode
                    _isDarkThemeLive.value = isDarkTheme
                }
            }
        }
    init {
        _nightModeLive.value = nightMode
        //监听 SharedPrefrence 更新
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangedListener)
    }



    override fun isFollowRedirect(): Boolean =
        sharedPreferences.getBoolean(KEY_FOLLOW_REDIRECT, true)


    override fun hasConsentStatus(): Boolean =
        sharedPreferences.getBoolean(KEY_HAS_CONSENT_STATUS, false)

    override fun updateNightMode(value:Int) {
        sharedPreferences.edit().putInt(PREFERENCE_NIGHT_MODE,value).apply()
    }
}
