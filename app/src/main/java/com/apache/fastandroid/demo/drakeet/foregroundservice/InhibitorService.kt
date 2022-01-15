package com.apache.fastandroid.demo.drakeet.foregroundservice

import android.app.IntentService
import android.content.Intent
import android.os.SystemClock
import com.apache.fastandroid.R
import com.blankj.utilcode.util.NotificationUtils
import com.orhanobut.logger.Logger

/**
 * Created by Jerry on 2022/1/8.
 */
class InhibitorService:IntentService("InhibitorService") {
    private val channel = "Channel"
    private val channelId = 2019
    override fun onHandleIntent(intent: Intent?) {
        SystemClock.sleep(200)
        // stopForeground(true)
        Logger.d("InhibitorService onHandleIntent is end")

    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Logger.d("InhibitorService onStart")
        // startForeground()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Logger.d("InhibitorService onStartCommand")

        return super.onStartCommand(intent, flags, startId)
    }

   private fun startForeground(){
       Logger.d("InhibitorService startForground")
       val notification = NotificationUtils.getNotification(NotificationUtils.ChannelConfig.DEFAULT_CHANNEL_CONFIG){
           //accept 模式
           it.setSmallIcon(R.drawable.icon_category)
           it.setContentText("Notifcation title")
           it.setProgress(0,0,true)
       }
       startForeground(channelId,notification)

   }

    override fun onDestroy() {
        super.onDestroy()
    }


}