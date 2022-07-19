package com.apache.fastandroid.demo.skydoves

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentSandswitchBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/5/1.
 */
class SandWitchDemoFragment:BaseVBFragment<FragmentSandswitchBinding>(FragmentSandswitchBinding::inflate) {

    private val mViewModel:SandSwitchViewModel by viewModels()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnByCall.setOnClickListener {

            mViewModel.loadDataByCall()

        }
        mBinding.btnApiResponseCoroutine.setOnClickListener {
            mViewModel.loadByCoroutine()
        }

        mViewModel.posterListLiveData.observe(this){
            println("posterListLiveData:${it?.size}")
        }
        mViewModel.toastLiveData.observe(this){
            toast(it)
        }

    }


}