package com.apache.fastandroid.demo.component

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import com.apache.fastandroid.component.timerecorder.AICheckEventListener
import com.apache.fastandroid.component.timerecorder.data.AICheckTraceModel
import com.apache.fastandroid.component.timerecorder.data.AIDataPoolImpl
import com.apache.fastandroid.databinding.FragmentComponentModeListBinding
import com.apache.fastandroid.demo.component.activityresult.ActivityResultDemoFragment
import com.apache.fastandroid.demo.component.dialogchanin.ADialog
import com.apache.fastandroid.demo.component.dialogchanin.BDialog
import com.apache.fastandroid.demo.component.dialogchanin.CDialog
import com.apache.fastandroid.demo.component.interval.IntervalDemoFragment
import com.csd.dialogchain.DialogChain
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.*

/**
 * Created by Jerry on 2023/3/12.
 */
class ComponentDemoFragment:BaseBindingFragment<FragmentComponentModeListBinding>(FragmentComponentModeListBinding::inflate) {
    private val bDialog by lazy { BDialog(requireContext()) }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnTimerRecord.setOnClickListener {
            timerRecord()
        }

        mBinding.btnInterval.setOnClickListener {
            intervalTest()
        }

        mBinding.btnActivityResult.setOnClickListener {
            FragmentContainerActivity.launch(requireActivity(),ActivityResultDemoFragment::class.java)
        }



        mBinding.btnDialogChain.setOnClickListener {
            dialogChain()

            dialogChain.process()

            // 模拟延迟数据回调。
            Handler().postDelayed({
                bDialog.onDataCallback("延迟数据回来了！！")
            },10000)
        }


    }

    private lateinit var dialogChain: DialogChain

    private fun dialogChain() {
        dialogChain = DialogChain.create(3)
            .attach(this)
            .addInterceptor(ADialog(requireContext()))
            .addInterceptor(bDialog)
            .addInterceptor(CDialog(requireContext()))
            .build()
    }


    private fun intervalTest() {
        FragmentContainerActivity.launch(requireActivity(), clazz = IntervalDemoFragment::class.java)
    }

    private fun timerRecord() {
        val eventListener = AICheckEventListener()

        GlobalScope.launch(Dispatchers.Main) {
            eventListener.callStart()
            withContext(Dispatchers.IO){
                delay(200)
                eventListener.processStart()

                delay(300)
                eventListener.processEnd()
                delay(300)
                eventListener.serverStart()

                delay(300)
                eventListener.serverEnd()

                delay(100)
                eventListener.asrSessionStart()

                delay(100)
                eventListener.asrSessionEnd()
            }

            eventListener.callEnd()

            val traceList = mutableListOf<AICheckTraceModel>()
            val values = AIDataPoolImpl.getInstance().getTraceModelMap().values
            if (!values.isEmpty()) {
                traceList.addAll(values)
            }
            traceList.sortByDescending {
                it.time
            }

            Logger.d("traceList:${traceList[0]}")
        }
    }


}