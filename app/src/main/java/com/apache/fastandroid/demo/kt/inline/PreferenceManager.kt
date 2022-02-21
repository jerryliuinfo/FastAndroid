package com.apache.fastandroid.demo.kt.inline

import android.content.SharedPreferences


private const val KEY_TOKEN = "token"
/**
 * Created by Jerry on 2022/2/19.
 */
class PreferenceManager(private val preferences: SharedPreferences) {

    fun saveToken(token: String){
        preferences.edit(commit = true){
            putString(KEY_TOKEN, token)
        }

        var dummy = 3
        preferences.edit2 (importantAction = { dummy = this }){
            putString(KEY_TOKEN, token)
        }
    }
}