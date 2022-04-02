package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentBestPracticeLivedataBinding
import com.apache.fastandroid.jetpack.viewmodel.WeatherInfoViewModel
import com.apache.fastandroid.jetpack.viewmodel.WeatherViewModelFactory
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataBestPracticeFragment : BaseVBFragment<FragmentBestPracticeLivedataBinding>(FragmentBestPracticeLivedataBinding::inflate){
    companion object{
        val TAG = "LiveDataBestPracticeFragment"
    }

    private val viewModel:WeatherInfoViewModel by viewModels { WeatherViewModelFactory }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewModel.currentTime.observe(this){
            mBinding.time.text = it.toString()
        }
        viewModel.currentTimeTransformed.observe(this){
            mBinding.timeTransformed.text = it
        }

        viewModel.currentWeather.observe(this){
            mBinding.currentWeather.text = it
        }

        viewModel.cacheData.observe(this){
            mBinding.cachedValue.text = it
        }

        mBinding.refreshButton.setOnClickListener {
            viewModel.onRefresh()
        }
    }


}