package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewConfiguration
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.MainActivity
import com.apache.fastandroid.databinding.TempApiUsageDemoBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.thread


/**
 * Created by Jerry on 2021/10/27.
 */
class ApiDemoFragment:BaseBindingFragment<TempApiUsageDemoBinding>(TempApiUsageDemoBinding::inflate) {
    companion object{
        private  const val TAG = "ApiDemoFragment"
    }

    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnTouchSlop.setOnClickListener {
            scaleTouchSlop()
        }

        mBinding.btnDirection.setOnClickListener {
            getLayoutDirection()
        }

        mBinding.btnCountDown.setOnClickListener {
            testCountDownLatch1()
        }

        mBinding.btnCountDown2.setOnClickListener {
            testCountDownLatch2()
        }
        mBinding.btnCountDown2.setOnClickListener {
            testCountDownLatch2()
        }
        mBinding.btnCountdownWaitConfirmDialog.setOnClickListener {
            confirmDialogCountDown()
        }

        mBinding.btnBreakPoint.setOnClickListener {
            testBreakPoint()
        }

        mBinding.btnStream.setOnClickListener {
            testStream()
        }

        mBinding.btnStream.setOnClickListener {
            testArchTaskExecutor()
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



    private val mCountDownLatch = CountDownLatch(1)

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


    private fun confirmDialogCountDown(){
        lifecycleScope.launch(){
            try {
                withContext(Dispatchers.IO){
                    mCountDownLatch.await()
                }

            }catch (e:Exception){
                e.printStackTrace()

            }finally {
                launchActivity<MainActivity>(requireContext())
            }
        }
        showConfirmDialog {
            mCountDownLatch.countDown()
        }

    }

    private fun showConfirmDialog(block:() -> Unit){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("请确认隐私协议")
            .setMessage("点击确认代表您同意隐私协议")
            .setPositiveButton("确认"){ dialog,_ ->
                dialog.dismiss()
                block.invoke()
            }
            .show()
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
/*

    override fun bindView(): TempApiUsageDemoBinding {
        return TempApiUsageDemoBinding.inflate(layoutInflater)
    }
*/


}