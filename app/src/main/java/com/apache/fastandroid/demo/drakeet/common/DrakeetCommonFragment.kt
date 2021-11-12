package com.apache.fastandroid.demo.drakeet.common

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.apache.fastandroid.MainActivity
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.UserBean
import com.apache.fastandroid.jetpack.lifecycle.service.MyService
import com.tesla.framework.common.util.AndroidVersion
import com.tesla.framework.common.util.CommonUtil
import com.tesla.framework.common.util.toast.ToastCompat
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_drakeet_knowledge.*

/**
 * Created by Jerry on 2021/10/15.
 */
class DrakeetCommonFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_drakeet_knowledge
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
            showNotification()
        }
        btn_chekcsdkIntAtLeast.setOnClickListener {
            CommonUtil.fromApi(26) {
                println("api above 26, do something")
            }
        }
    }

    /**
     * https://t.zsxq.com/Nvzjyn2
     */
    private fun showNotification(){
        val context = activity
        val intent = Intent(context,MainActivity::class.java).apply {
            action = "Action_1"
            addFlags(CommonUtil.getFlagsCompat(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP))
            putExtra("user",UserBean("zhangsan"))
            putExtra("key","value")
        }

        val channelId = "test_channel2"
        val replyIntent = PendingIntent.getActivity(context,201,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val manager = NotificationManagerCompat.from(context!!)
        val channel = manager.getNotificationChannel(channelId)
        if (channel == null){
            createNotificationChannel(channelId)?.let {
                manager.createNotificationChannel(it)
            }
        }
        val notification = NotificationCompat.Builder(context,channelId)
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