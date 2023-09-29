package com.apache.fastandroid.setting

import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.apache.fastandroid.R
import com.apache.fastandroid.util.GlobalConstans
import com.apache.fastandroid.util.GlobalValues
import com.tesla.framework.ui.fragment.preference.BasePreferenceLoader

/**
 * Created by Jerry on 2023/9/29.
 */
class SettingsPreferenceLoader(fragment: PreferenceFragmentCompat) :
    BasePreferenceLoader(fragment) {
    override fun loadPreferences() {

        loadPreferences(R.xml.settings)

        (findPreference(GlobalConstans.SpKey.WORK_MODE) as ListPreference).apply {
            setOnPreferenceChangeListener { preference, newValue ->
                GlobalValues.workMode = newValue as String
                true
            }
        }

        findPreference(GlobalConstans.SpKey.PREF_WEBDAV_HOST)?.apply {
            setOnPreferenceChangeListener { preference, newValue ->
                GlobalValues.webdavHost = newValue.toString()
                preference.summary = newValue.toString()
                true
            }
            summary = GlobalValues.webdavHost
        }
    }
}