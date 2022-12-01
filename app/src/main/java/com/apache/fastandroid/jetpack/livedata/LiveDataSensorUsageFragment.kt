package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.apache.fastandroid.databinding.FragmentJetpackLivedataSeniorUsageBinding
import com.apache.fastandroid.demo.extension.onTextChanged
import com.apache.fastandroid.demo.extension.setTextAndMaintainSelection
import com.tesla.framework.component.livedata.distinct
import com.tesla.framework.component.livedata.map
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.showToast

import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataSensorUsageFragment : BaseBindingFragment<FragmentJetpackLivedataSeniorUsageBinding>(FragmentJetpackLivedataSeniorUsageBinding::inflate){

    val mNameLiveData = MutableLiveData<String>()

    val mValidationMode = MutableLiveData<Boolean>()


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        mBinding.etName.onTextChanged (2000){
            mNameLiveData.value = it
        }

        mBinding.btnLivedataDistinct.setOnClickListener {
            mNameLiveData.distinct()
                .observe(this, Observer {
                    Logger.d("observer text:${it}")
                    showToast(it)
                    mBinding.etName.setTextAndMaintainSelection(it)
                })
        }



        val nameLengthLiveData:LiveData<Int> = mNameLiveData.map {
            it.length
        }

        nameLengthLiveData .observe(this) { length ->
            Logger.d("length: $length")
        }


    }

}