package com.apache.fastandroid.demo.blacktech

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentBlacktechEncryptBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/4/15.
 */
class EncryptDemoFragment:BaseVBFragment<FragmentBlacktechEncryptBinding>(FragmentBlacktechEncryptBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }
}