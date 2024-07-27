package com.apache.fastandroid.jetpack.coroutine

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentRengwuxianCouritineBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import kotlin.concurrent.thread
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Created by Jerry on 2024/6/23.
 * IO 操作期间，CPU 是闲置的，
 * 从磁盘读写数据的操作是由磁盘操作的，网络调用接口的操作是 "网卡"操作的， cpu 只是把指令交给它们
 * Dispatchers.DEFAULT 和 Dispatchers.IO 的区别:
 * DEFAULT:
 */
class CoroutineKnowledgeDemoFragment:BaseBindingFragment<FragmentRengwuxianCouritineBinding>(FragmentRengwuxianCouritineBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnSwitchThread.setOnClickListener {
            switchThread()
        }
    }

    private fun switchThread() {
        thread {

        }
        println("Main Thread:${Thread.currentThread().name}")

        val executor = Executors.newCachedThreadPool()
        executor.execute {
            println("Executor Thread:${Thread.currentThread().name}")
        }

        val scope = CoroutineScope(EmptyCoroutineContext)
        scope.launch {
            println("Coroutine Thread:${Thread.currentThread().name}")
        }
        ContinuationInterceptor

    }
}