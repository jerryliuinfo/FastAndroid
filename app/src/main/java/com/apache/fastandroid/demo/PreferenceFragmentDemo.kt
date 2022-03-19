package com.apache.fastandroid.demo

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseVBFragment
import com.tesla.framework.kt.SPreference.Companion.preference

/**
 * Created by Jerry on 2022/3/19.
 */
class PreferenceFragmentDemo: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences,rootKey)

    }
}