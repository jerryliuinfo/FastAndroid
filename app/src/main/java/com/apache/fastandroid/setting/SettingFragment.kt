package com.apache.fastandroid.setting

import android.os.Bundle
import android.view.LayoutInflater
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentAdvanceNavAboutBinding
import com.apache.fastandroid.util.GlobalConstans
import com.apache.fastandroid.util.GlobalValues
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/16.
 */
class SettingFragment:PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings,rootKey)

        findPreference<ListPreference>(GlobalConstans.SpKey.WORK_MODE)?.apply {
            setOnPreferenceChangeListener { preference, newValue ->
                GlobalValues.workMode = newValue as String
                true
            }
        }

        findPreference<Preference>(GlobalConstans.SpKey.PREF_WEBDAV_HOST)?.apply {
            setOnPreferenceChangeListener { preference, newValue ->
                GlobalValues.webdavHost = newValue.toString()
                preference.summary = newValue.toString()
                true
            }
            summary = GlobalValues.webdavHost
        }
    }

}