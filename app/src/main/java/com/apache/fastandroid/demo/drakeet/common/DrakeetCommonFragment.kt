package com.apache.fastandroid.demo.drakeet.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.HandlerCompat
import com.apache.fastandroid.LogUtils
import com.apache.fastandroid.MainActivity
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDrakeetKnowledgeBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.temp.bean.ReflectBean
import com.apache.fastandroid.jetpack.lifecycle.service.MyService
import com.blankj.utilcode.util.ReflectUtils
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.common.device.DeviceName
import com.tesla.framework.common.util.AndroidVersion
import com.tesla.framework.common.util.CommonUtil
import com.tesla.framework.common.util.DrakeetUtils
import com.tesla.framework.common.util.DrakeetUtils.doOnMainThreadIdle
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.common.util.toast.ToastCompat
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.fragment_drakeet_knowledge.*
import java.lang.Exception

/**
 * Created by Jerry on 2021/10/15.
 */
class DrakeetCommonFragment:BaseVBFragment<FragmentDrakeetKnowledgeBinding>(FragmentDrakeetKnowledgeBinding::inflate) {

    companion object{
        private const val TAG = "DrakeetCommonFragment";
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        //https://wx.zsxq.com/dweb2/index/topic_detail/581111245444844

        btn_toast.setOnClickListener {
            ToastCompat.makeText(context, "i am toast",ToastCompat.LENGTH_SHORT).show()
        }
        btn_judge_sdk_version.setOnClickListener {
            AndroidVersion.isAndroid12()
        }

        btn_service_ontask_removed.setOnClickListener {
            context?.let { it1 -> MyService.start(it1) }
        }

        btn_samsung_notification_bug.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showNotification()
            }
        }
        btn_chekcsdkIntAtLeast.setOnClickListener {
            CommonUtil.fromApi(26) {
                println("api above 26, do something")
            }
        }
        btn_doOnMainThreadIdle.setOnClickListener {
            btn_doOnMainThreadIdle.doOnMainThreadIdle({
                  NLog.d(TAG," do something on main Thread idle")
            },4000)
        }
        btn_string_hash_conflict.setOnClickListener {
            NLog.d(TAG, "Aa's hashCode is:${"Aa".hashCode()}, BB' s hashCode is: ${"BB".hashCode()}")
        }
        var i = 0;
        btn_print_call_chain.setOnClickListener {
            i++
            if (i > 3){
                i = 0;
                var stackTrace = DrakeetUtils.stackTrace(null, 10)
                NLog.d(TAG, "stackTrace: %s",stackTrace)
            }
        }
        btn_getDeviceName.setOnClickListener {
            //https://t.zsxq.com/bE2znqV
            kotlin.runCatching {
                var deviceName = DeviceName.getDeviceName("clark", "Unknown device")
                Logger.d("deviceName:${deviceName}")
                DeviceName.with(context).request { deviceInfo:DeviceName.DeviceInfo?, exception:Exception? ->
                    LogUtils.d("deviceName2:${deviceInfo}")

                }
            }
        }


        //btn_launchMode

        /**
         *  https://t.zsxq.com/BaiIUv3
         */
        btn_launchMode.setOnClickListener {

        }
        btn_restart.setOnClickListener {
            DrakeetUtils.triggerRestart(requireContext(),null)
        }
        /**
         * https://t.zsxq.com/6mI2jU3
         * 从API28开始，Handler类增加了静态方法createAsyn。主要作用是使所有通过这个Handler发送的Message，
         * 都会被设置为FLAG_ASYNCHRONOUS异步消息(默认是同步消息)，在搭配消息屏障使用的情况下，会被优先调用
         */
        btn_async_handler.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 28){
                HandlerCompat.createAsync(Looper.myLooper()!!).postDelayed({
                    println("post delay with async handler")
                },500)
            }

        }

        //https://t.zsxq.com/eeIeU3n
        btn_reflect.setOnClickListener {
            ReflectUtils.reflect(ReflectBean::class.java).field("sCacheList", arrayListOf("Jerry","Tom"))
            var sCacheList:List<String> = ReflectUtils.reflect(ReflectBean::class.java).field("sCacheList").get<List<String> >()
            com.tesla.framework.component.logger.Logger.d("length:${sCacheList}")
        }


        Logger.d("context file dir:${requireContext().filesDir}, cache:${requireContext().cacheDir}, " +
                "externalFileDir:${requireContext().getExternalFilesDir(null)}, externalCacheDir:${requireContext().externalCacheDir}",
        )

    }

    /**
     * https://t.zsxq.com/Nvzjyn2
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification(){
        val context = activity
        val intent = Intent(context,MainActivity::class.java).apply {
            action = "Action_1"
            addFlags(CommonUtil.getFlagsCompat(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
            putExtra("user", UserBean("zhangsan"))
            putExtra("key","value")
        }

        val channelId = "test_channel2"
        val replyIntent = PendingIntent.getActivity(context,201,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val manager = NotificationManagerCompat.from(requireContext())
        val channel = manager.getNotificationChannel(channelId)
        if (channel == null){
            createNotificationChannel(channelId)?.let {
                manager.createNotificationChannel(it)
            }
        }
        val notification = NotificationCompat.Builder(requireContext(),channelId)
            .setOnlyAlertOnce(true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setCategory(NotificationCompat.CATEGORY_EMAIL)
            .setLocalOnly(true)
            .setLights(Color.BLUE,500,1000)
            .setAutoCancel(false)
            .setContentTitle("Test notification")
            .setContentText("Click reply action button")
            .setContentIntent(replyIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
        manager.notify(1230,notification)

    }

    private fun createNotificationChannel(channelId:String):NotificationChannel? {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ChannelName"
            val descriptionText = "ChannelDescription"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            return channel
        }
        return null
    }

}