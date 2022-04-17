package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.TempAsynctosyncUsageDemoBinding
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.temp_asynctosync_usage_demo.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.thread


/**
 * Created by Jerry on 2021/10/27.
 */
class AsyncToSyncDemoFragment:BaseVBFragment<TempAsynctosyncUsageDemoBinding>(TempAsynctosyncUsageDemoBinding::inflate) {
    companion object{
        private  const val TAG = "ApiDemoFragment"
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_countdown.setOnClickListener {
            testCoundDownLatcher()
        }
    }

    private val countDownLatch = CountDownLatch(3)
    private fun testCoundDownLatcher(){
        for (index in 0 until 3){
            thread {
                Thread.sleep(1000 + ThreadLocalRandom.current().nextInt(1000).toLong())
                NLog.d(TAG, "finish ${index} + ${Thread.currentThread().name}")
                countDownLatch.countDown()
            }
        }

        countDownLatch.await()
        NLog.d(TAG, "主线程执行完毕")
    }



}