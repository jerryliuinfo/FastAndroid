package com.apache.fastandroid.demo.blacktech

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentBlacktechEncryptBinding
import com.apache.fastandroid.demo.blacktech.encrypt.ParanoidSample
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/4/15.
 */
class EncryptDemoFragment:BaseVBFragment<FragmentBlacktechEncryptBinding>(FragmentBlacktechEncryptBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnParanoid.setOnClickListener {
            paranoidUsage()
        }

    }

    /**
     * https://github.com/MichaelRocks/paranoid
     */
    private fun paranoidUsage() {
        val sample = ParanoidSample()
        val text = sample.text1() +"\n" + sample.text2()
        mBinding.tvResult.text = text

    }
}