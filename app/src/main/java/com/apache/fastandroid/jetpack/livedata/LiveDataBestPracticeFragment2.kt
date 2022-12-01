package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentBestPracticeLivedata2Binding
import com.apache.fastandroid.jetpack.viewmodel.WeatherInfoViewModel
import com.apache.fastandroid.jetpack.viewmodel.WeatherViewModelFactory
import com.tesla.framework.ui.fragment.BaseDataBindingFragment

/**
 * Created by Jerry on 2020/11/5.
 * https://github.com/android/architecture-components-samples/tree/main/LiveDataSample
 */
class LiveDataBestPracticeFragment2 : BaseDataBindingFragment<FragmentBestPracticeLivedata2Binding>(FragmentBestPracticeLivedata2Binding::inflate){
    companion object{
        val TAG = "LiveDataBestPracticeFragment"
    }

    private val viewModel:WeatherInfoViewModel by viewModels { WeatherViewModelFactory }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.viewmodel = viewModel
    }


}