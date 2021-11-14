package com.apache.fastandroid.demo.temp

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewConfiguration
import androidx.core.view.ViewCompat
import com.apache.fastandroid.MainActivity
import com.apache.fastandroid.R
import com.blankj.utilcode.util.SPUtils
import com.tcl.account.accountsync.IAccountCallback
import com.tcl.account.accountsync.UserInfoAsyncManager
import com.tcl.account.accountsync.bean.AccountBean
import com.tcl.account.accountsync.bean.TclAccountBuilder
import com.tcl.account.accountsync.bean.TclConfig
import com.tcl.account.accountsync.util.TclAccoutException
import com.tesla.framework.common.util.log.NLog
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

        btn_breakPoint.setOnClickListener {
            for ( i in 1 until 10){
                if (i % 2 == 0){
                    if (i > 4){
                        println("$i")
                    }
                }
            }
        }
        btn_sync_account.setOnClickListener {
            val config = TclConfig()
            //设置正确的appId
            //设置正确的appId
            config.setAppId("46121610438946717")
            TclAccountBuilder.getInstance().init(config, activity)
            doQuery()
        }
        btn_sync_account_error.setOnClickListener {
            val config = TclConfig()
            //设置正确的appId
            //设置正确的appId
            config.setAppId("aaaaaa")
            TclAccountBuilder.getInstance().init(config, activity)
            doQuery()
        }
        btn_stream.setOnClickListener {
            var function: (Int) -> Boolean = { it % 2 == 0 }
            var filterList = arrayListOf(1, 3, 4, 6, 7, 8).stream().filter(function)
            NLog.d(TAG, "filterList: %s",filterList)
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


    private fun doQuery(){
        UserInfoAsyncManager.getInstance()
            .queryTclAccountInfo(object : IAccountCallback<AccountBean> {
                override fun onSuccess(userBean: AccountBean) {
                    val msg =
                        "查询成功:accountId:" + userBean.getAccountId() + ", phone:" + userBean.getPhone()
                    NLog.d(MainActivity.TAG, "onSuccess msg: %s", msg)
                }

                override fun onFailure(exception: TclAccoutException) {
                    val msg = "查询失败:code:" + exception.code + ", msg:" + exception.msg
                    NLog.d(MainActivity.TAG, "onFailure: %s", msg)
                }
            }, activity)
    }

}