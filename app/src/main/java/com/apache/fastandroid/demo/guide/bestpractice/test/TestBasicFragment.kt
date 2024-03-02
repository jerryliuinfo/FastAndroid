package com.apache.fastandroid.demo.guide.bestpractice.test

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentBestPracticeTestBasicBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2024/3/2.
 */
class TestBasicFragment:BaseBindingFragment<FragmentBestPracticeTestBasicBinding>(FragmentBestPracticeTestBasicBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)



    }
}