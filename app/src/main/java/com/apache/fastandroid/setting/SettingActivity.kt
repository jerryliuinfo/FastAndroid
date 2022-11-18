package com.apache.fastandroid.setting

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ActivitySettingsBinding
import com.apache.fastandroid.util.GlobalConstans
import com.apache.fastandroid.util.GlobalValues
import com.tesla.framework.ui.activity.BaseVBActivity

/**
 * Created by Jerry on 2022/5/2.
 */
class SettingActivity : BaseVBActivity<ActivitySettingsBinding>(ActivitySettingsBinding::inflate) {

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

    }

    class SettingFragment:PreferenceFragmentCompat(){
        init {

        }
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings,rootKey)

            findPreference<ListPreference>(GlobalConstans.SpKey.WORK_MODE)?.apply {
                setOnPreferenceChangeListener { preference, newValue ->
                    GlobalValues.workMode = newValue as String
                    true
                }
            }
        }


    }
}