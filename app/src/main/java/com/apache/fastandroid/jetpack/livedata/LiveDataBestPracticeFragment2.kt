package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentBestPracticeLivedata2Binding
import com.apache.fastandroid.databinding.FragmentBestPracticeLivedataBinding
import com.apache.fastandroid.jetpack.viewmodel.WeatherInfoViewModel
import com.apache.fastandroid.jetpack.viewmodel.WeatherViewModelFactory
import com.tesla.framework.ui.fragment.BaseDBFragment
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataBestPracticeFragment2 : BaseDBFragment<FragmentBestPracticeLivedata2Binding>(FragmentBestPracticeLivedata2Binding::inflate){
    companion object{
        val TAG = "LiveDataBestPracticeFragment"
    }

    private val viewModel:WeatherInfoViewModel by viewModels { WeatherViewModelFactory }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

       viewBinding.lifecycleOwner = this
        viewBinding.viewmodel = viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_best_practice_livedata2
    }


}