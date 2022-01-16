package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.core.view.ViewCompat
import com.apache.fastandroid.MainActivity
import com.apache.fastandroid.R
import com.apache.fastandroid.adapter.FlowTagAdapter
import com.apache.fastandroid.databinding.TempApiUsageDemoBinding
import com.blankj.utilcode.util.SPUtils
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.temp_api_usage_demo.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.thread


/**
 * Created by Jerry on 2021/10/27.
 */
class ApiDemoFragment:BaseVBFragment<TempApiUsageDemoBinding>() {
    companion object{
        private  const val TAG = "ApiDemoFragment"
    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.temp_api_usage_demo
//    }

    private val tags = mutableListOf<String>(
        "获取触发移动事件的最小距离",
        "android多语言适配，获取布局方向",
        "CountDown让多个线程等待",
        "CountDown让单个线程等待",
        "BreakPoint调试",

        "同步账号信息(正确)",
        "同步账号信息(错误)",
        "集合流操作",
        "ArchTaskExecutor"
    )



    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        val adapter = FlowTagAdapter(context)
        flowlayout_normal_select.adapter = adapter
        adapter.addTags(tags)

        flowlayout_normal_select.setOnTagClickListener { parent, view, position ->
            when (position) {
                0 -> scaleTouchSlop()
                1 -> getLayoutDirection()
                2 -> testCountDownLatch1()
                3 -> testCountDownLatch2()
                4 -> testBreakPoint()
                7 -> testStream()
                8 -> testArchTaskExecutor()
            }
        }

    }

    @SuppressLint("RestrictedApi")
    private fun testArchTaskExecutor(){
        ArchTaskExecutor.getInstance().executeOnDiskIO {

            NLog.d(TAG, "executeOnDiskIO thread:%s--->",Thread.currentThread().name)
        }
        ArchTaskExecutor.getInstance().executeOnMainThread() {
            NLog.d(TAG, "executeOnMainThread thread:%s--->",Thread.currentThread().name)
        }
    }

    private fun testStream(){
        var function: (Int) -> Boolean = { it % 2 == 0 }
        var filterList = arrayListOf(1, 3, 4, 6, 7, 8).stream().filter(function)
        NLog.d(TAG, "filterList: %s",filterList)
    }

    private fun testBreakPoint(){
        for ( i in 1 until 10){
            if (i % 2 == 0){
                if (i > 4){
                    println("$i")
                }
            }
        }
    }



    fun scaleTouchSlop(){
        var scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        NLog.d(TAG, "scaledTouchSlop value:${scaledTouchSlop}")
    }

    fun getLayoutDirection(){
//        var layoutDirection = ViewCompat.getLayoutDirection(liner1)
//        NLog.d(TAG, "layoutDirection value:${layoutDirection}")
    }


    /**
     * 让多个线程等待, 模拟并发，让并发线程一起执行
     */
    private fun testCountDownLatch1(){
        GlobalScope.launch (Dispatchers.IO){
            val countDownLatch = CountDownLatch(1)
            for (index in 0..5){
                thread {
                    countDownLatch.await()
                    val parter = "[ index:${index}, ${Thread.currentThread().name} ]"
                    NLog.d(TAG, "${parter} 开始执行")
                }
            }
            Thread.sleep(2000)
            countDownLatch.countDown()
        }

    }

    /**
     * 让单个线程等待, 多个任务完成后进行汇总合作
     */
    private fun testCountDownLatch2(){
        GlobalScope.launch (Dispatchers.IO){
            val count = 5
            val countDownLatch = CountDownLatch(count)
            for (index in 0 until count){
                thread {
                    Thread.sleep((1000 + ThreadLocalRandom.current().nextInt(1000)).toLong())
                    NLog.d(TAG, "finish ${index} + ${Thread.currentThread().name}")
                    countDownLatch.countDown()
                }
            }
            countDownLatch.await()
            NLog.d(TAG, "主线程在所有任务完成后进行汇总")
        }

    }

    override fun bindView(): TempApiUsageDemoBinding {
        return TempApiUsageDemoBinding.inflate(layoutInflater)
    }


}