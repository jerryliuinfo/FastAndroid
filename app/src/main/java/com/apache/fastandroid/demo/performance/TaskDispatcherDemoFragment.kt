package com.apache.fastandroid.demo.performance

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.PerformanceTaskDispatcherBinding
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.component.DelayInitTaskDispatcher
import com.tesla.framework.component.ITask
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/11/15.
 */
class TaskDispatcherDemoFragment: BaseBindingFragment<PerformanceTaskDispatcherBinding>(PerformanceTaskDispatcherBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        mBinding.btnIdleHandler.setOnClickListener {
            ToastUtils.showShort("click")

            val dispatcher = DelayInitTaskDispatcher()
            dispatcher.addTask(object :ITask{
                override fun execute() {
                }

            }).addTask(object :ITask{
                override fun execute() {
                }

            }).start()

        }
    }
}