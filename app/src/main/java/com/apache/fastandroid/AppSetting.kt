package com.apache.fastandroid

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by Jerry on 2022/5/21.
 */
class AppSetting(private val sharedPreferences: SharedPreferences) {

    var nightMode:Int = PREFERENCE_NIGHT_MODE_DEF_VAL
        get() = sharedPreferences.getInt(PREFERENCE_NIGHT_MODE,PREFERENCE_NIGHT_MODE_DEF_VAL)
        set(value) {
            sharedPreferences.edit().putInt(PREFERENCE_NIGHT_MODE,value).apply()
            AppCompatDelegate.setDefaultNightMode(value)
            field = value
        }


    private val _nightModeLive = MutableLiveData<Int>()
    val nightModeLive:LiveData<Int> = _nightModeLive

    val isDarkThme:Boolean
        get() = nightMode == AppCompatDelegate.MODE_NIGHT_YES


    private val _isDarkThemeLive = MutableLiveData<Boolean>()
    val isDarkThemeLive:LiveData<Boolean> = _isDarkThemeLive

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener{ _,key ->
        when(key){
            PREFERENCE_NIGHT_MODE -> {
                _nightModeLive.value = nightMode
                _isDarkThemeLive.value = isDarkThme
            }
        }
    }

    init {
        _nightModeLive.value = nightMode
        _isDarkThemeLive.value = isDarkThme
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener)

    }

    companion object {
        private const val PREFERENCE_NIGHT_MODE = "preference_night_mode"
        private const val PREFERENCE_NIGHT_MODE_DEF_VAL = AppCompatDelegate.MODE_NIGHT_NO
    }
}