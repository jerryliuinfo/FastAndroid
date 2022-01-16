package com.apache.fastandroid.demo.temp

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewConfiguration
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import com.apache.fastandroid.MainActivity
import com.apache.fastandroid.R
import com.blankj.utilcode.util.SPUtils
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.support.action.IAction
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.temp_api_usage_demo.*
import kotlinx.android.synthetic.main.temp_asynctosync_usage_demo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.thread


/**
 * Created by Jerry on 2021/10/27.
 */
class AsyncToSyncDemoFragment:BaseFragment() {
    companion object{
        private  const val TAG = "ApiDemoFragment"
    }

    override fun getLayoutId(): Int {
        return R.layout.temp_asynctosync_usage_demo
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