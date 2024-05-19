package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatDelegate
import com.apache.fastandroid.app.FastApplication
import com.apache.fastandroid.databinding.FragmentTempKnowledge3Binding
import com.apache.fastandroid.demo.preference.DefaultPreferences
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment


/**
 * Created by Jerry on 2021/9/6.
 */
class Knowledge3Fragment: BaseBindingFragment<FragmentTempKnowledge3Binding>(FragmentTempKnowledge3Binding::inflate) {
    companion object{
        private const val TAG = "Knowledge3Fragment"
    }

    private val mDefaultPreferences:DefaultPreferences by lazy {
        (activity?.application as FastApplication).mDefaultPreferences
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnNightModeYes.setOnClickListener {
            updateNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        mBinding.btnNightModeNo.setOnClickListener {
            updateNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        mDefaultPreferences.nightModeLive.observe(this){
            Logger.d("night mode changed:$it")
            // (activity as AppCompatActivity).delegate.localNightMode = it
        }

    }


    private fun updateNightMode(mode:Int){
        mDefaultPreferences.updateNightMode(mode)
    }
}