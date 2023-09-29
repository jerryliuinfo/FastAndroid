package com.apache.fastandroid.setting

import com.tesla.framework.ui.fragment.preference.PreferenceLoaderFragment

/**
 * Created by Jerry on 2022/3/16.
 */
class SettingFragment : PreferenceLoaderFragment() {

    private lateinit var preferenceLoader: SettingsPreferenceLoader


    override fun loadPreferences() {
        preferenceLoader = SettingsPreferenceLoader(this)
        preferenceLoader.loadPreferences()
    }

}