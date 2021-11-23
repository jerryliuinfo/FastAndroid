package com.apache.fastandroid.demo.performance

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.DelayInitTaskDispatcher
import com.tesla.framework.component.ITask
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.performance_task_dispatcher.*

/**
 * Created by Jerry on 2021/11/15.
 */
class TaskDispatcherDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.performance_task_dispatcher
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        val dispatcher = DelayInitTaskDispatcher()
        btn_start.setOnClickListener {
            ToastUtils.showShort("click")
            dispatcher.addTask(object :ITask{
                override fun execute() {
                   NLog.d(TAG, "task1 execute")
                }

            }).addTask(object :ITask{
                override fun execute() {
                    NLog.d(TAG, "task2 execute")

                }

            }).start()

        }
    }
}