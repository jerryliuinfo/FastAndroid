package com.apache.fastandroid.demo.kt.coroutine.rengwuxian

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentCoroutineBasicBinding
import com.apache.fastandroid.demo.kt.coroutine.CoroutineVewModel
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.MainScope

/**
 * Created by Jerry on 2021/10/28.
 * 协程基础
 */
class CoroutineRengwuXianDemoFragment:BaseBindingFragment<FragmentCoroutineBasicBinding>(FragmentCoroutineBasicBinding::inflate) {
    companion object{

    }

    private val viewModel: CoroutineVewModel by viewModels()

    private val mainScope = MainScope()


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


    }

}

