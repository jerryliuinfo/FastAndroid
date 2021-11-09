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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.thread


/**
 * Created by Jerry on 2021/10/27.
 */
class ApiDemoFragment:BaseFragment() {
    companion object{
        private  const val TAG = "ApiDemoFragment"
    }

    override fun inflateContentView(): Int {
        return R.layout.temp_api_usage_demo
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        tv_get_scaled_touch_slop.setOnClickListener {
            scaleTouchSlop()
        }

        tv_get_LayoutDirection.setOnClickListener {
            getLayoutDirection()
        }
        btn_countdown_usage1.setOnClickListener {
            testCountDownLatch1()
        }
        btn_countdown_usage2.setOnClickListener {
            testCountDownLatch2()
        }

        btn_content_provider.setOnClickListener {
            if (!SPUtils.getInstance().getBoolean("authorized",false)
                || !TextUtils.isEmpty(SPUtils.getInstance().getString("phone"))){



            }else{

            }


        }

    }

    private val COLUM_NAME = arrayOf("accountId", "phone","email")

    fun queryUserInfo() {
        val resolver: ContentResolver = context!!.contentResolver
        val uri_user = Uri.parse("content://com.tcl.account/userInfo")
        val cursor: Cursor? = resolver.query(uri_user, COLUM_NAME, null, null, null)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val accountId = cursor.getInt(cursor.getColumnIndex(COLUM_NAME[0]))
                val phone = cursor.getString(cursor.getColumnIndex(COLUM_NAME[1]))
                val email = cursor.getString(cursor.getColumnIndex(COLUM_NAME[2]))
                NLog.d(MainActivity.TAG, "accountId: %s, phone:%s,email:%s", accountId, phone,email)
                SPUtils.getInstance()

            }
        }
    }


    fun scaleTouchSlop(){
        var scaledTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        NLog.d(TAG, "scaledTouchSlop value:${scaledTouchSlop}")
    }

    fun getLayoutDirection(){
        var layoutDirection = ViewCompat.getLayoutDirection(liner1)
        NLog.d(TAG, "layoutDirection value:${layoutDirection}")
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

}