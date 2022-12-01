package com.apache.fastandroid.demo.kt.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentCouritineBestPracticeBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2021/10/28.
 * 协程
 */
class CoroutineBestPracticeFragment :
    BaseBindingFragment<FragmentCouritineBestPracticeBinding>(FragmentCouritineBestPracticeBinding::inflate) {
    companion object {
        private const val TAG = "CoroutineDemoFragment"
    }

    private val mViewModel: CoroutineVewModel by viewModels()


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        /**
         * repeatOnLifecycle 是一个挂起函数，它以 Lifecycle.State 作为参数，用于在生命周期达到该状态时自动创建和启动一个新的协程，并将块传递给它，
         * 并在生命周期达到该状态时取消正在执行该块的正在进行的协程 低于状态。
         */
        lifecycleScope.launchWhenStarted {


        }


        /**
         *
         *
         * 使用数据流时，最好通过适当的协程作用域和 repeatOnLifecycle API 来处理这一任务：
         *
         */
        viewLifecycleOwner.lifecycleScope.launch {
           /* viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.map {
                    it.isLoading
                }.distinctUntilChanged()
                    .collect {
                        //loading state changed
                    }
            }

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.uiState.collect { uiState ->
                    uiState.userMessage?.let {
                        //show snackBar with message

                        mViewModel.userMessageShown()
                    }
                }
            }*/
        }


    }


}

