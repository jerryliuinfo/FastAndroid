package com.apache.fastandroid.demo.component

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.component.timerecorder.AICheckEventListener
import com.apache.fastandroid.component.timerecorder.data.AICheckTraceModel
import com.apache.fastandroid.component.timerecorder.data.AIDataPoolImpl
import com.apache.fastandroid.component.timerecorder.utils.RecordUtil
import com.apache.fastandroid.databinding.FragmentComponentModeListBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.*

/**
 * Created by Jerry on 2023/3/12.
 */
class ComponentDemoFragment:BaseBindingFragment<FragmentComponentModeListBinding>(FragmentComponentModeListBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnTimerRecord.setOnClickListener {
            timerRecord()
        }

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