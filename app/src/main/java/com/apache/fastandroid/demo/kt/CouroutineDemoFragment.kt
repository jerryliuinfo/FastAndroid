package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentKotlinCouritineBinding
import com.apache.fastandroid.util.extensitons.runOnUi
import com.tesla.framework.ui.fragment.BaseLifecycleFragment
import kotlinx.android.synthetic.main.fragment_kotlin_couritine.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

/**
 * Created by Jerry on 2021/10/28.
 */
class CouroutineDemoFragment:BaseLifecycleFragment<FragmentKotlinCouritineBinding>() {
    companion object{
        private const val TAG = "CouroutineDemoFragment"
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_kotlin_couritine
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_traditional_switch_thread.setOnClickListener {
            traditionalSwitchThread()
        }
        btn_coroutine_switch_thread.setOnClickListener {
            coroutineSwitchThread()
        }
    }

    private fun traditionalSwitchThread() {
        classicIoCode1 {
            uiCode1()
        }
        classicIoCode2 {
            uiCode2()
        }
        classicIoCode3 {
            uiCode3()
        }
    }

    private fun coroutineSwitchThread(){
        //GlobalScope.launc 创建的协程是在后台执行的
        GlobalScope.launch(Dispatchers.Main) {
            ioCode1()
            uiCode1()
            ioCode2()
            uiCode2()
            ioCode3()
            uiCode3()
        }
    }

    suspend fun ioCode1(){
        //切到子线程
        withContext(Dispatchers.IO){
            println("Coroutines Camp io1:${Thread.currentThread().name}")
        }

    }
    fun uiCode1(){
        println("Coroutines Camp ui1:${Thread.currentThread().name}")
    }

    suspend fun ioCode2(){
        //切到子线程
        withContext(Dispatchers.IO){
            println("Coroutines Camp io2:${Thread.currentThread().name}")
        }
    }

    fun uiCode2(){
        println("Coroutines Camp ui2:${Thread.currentThread().name}")
    }

    suspend fun ioCode3(){
        withContext(Dispatchers.IO){
            println("Coroutines Camp io3:${Thread.currentThread().name}")
        }
    }
    fun uiCode3(){
        println("Coroutines Camp ui3:${Thread.currentThread().name}")
    }


    private fun classicIoCode1(block: () -> Unit){
        thread {
            println("classic io1: ${Thread.currentThread().name}")
            runOnUi(block)
        }
    }
    private fun classicIoCode2(block: () -> Unit){
        thread {
            println("classic io2: ${Thread.currentThread().name}")
            runOnUi(block)
        }
    }
    private fun classicIoCode3(block: () -> Unit){
        thread {
            println("classic io3: ${Thread.currentThread().name}")
            runOnUi(block)
        }
    }

    override fun initViewModel() {
    }


}