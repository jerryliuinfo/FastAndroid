package com.apache.fastandroid.demo

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentWebviewBinding
import com.tesla.framework.ui.fragment.BaseDBFragment
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/8/5.
 */
class WebViewFragment:BaseVBFragment<FragmentWebviewBinding>(FragmentWebviewBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


    }
}