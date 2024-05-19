package com.apache.fastandroid.sample.materialthemebuilder.ui.instruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.app.FastApplication
import com.google.android.material.switchmaterial.SwitchMaterial

/**
 * Created by Jerry on 2024/5/19.
 */
class InstructionsFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_instructions, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val darkThemeSwitch: SwitchMaterial = view.findViewById(R.id.dark_theme_switch)
        val preferenceRepository = (requireActivity().application as FastApplication).mDefaultPreferences

        preferenceRepository.isDarkThemeLive.observe(this) { isDarkTheme ->
            isDarkTheme?.let { darkThemeSwitch.isChecked = it }
        }

        darkThemeSwitch.setOnCheckedChangeListener { _, checked ->
            preferenceRepository.isDarkTheme = checked
        }
    }
}