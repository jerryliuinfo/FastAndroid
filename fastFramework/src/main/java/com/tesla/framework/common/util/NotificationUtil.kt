package com.tesla.framework.common.util

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.tesla.framework.R

/**
 * Created by Jerry on 2024/1/20.
 */




fun createNotification(context: Context,channelId:String,  cancelText:String,name:String, notificationTitle: String,intent: PendingIntent): Notification {
    val builder = NotificationCompat.Builder(com.blankj.utilcode.util.Utils.getApp(), channelId)
        .setContentTitle(notificationTitle)
        .setTicker(notificationTitle)
        .setSmallIcon(R.drawable.baseline_gradient)
        .setOngoing(true)
        .addAction(android.R.drawable.ic_delete, cancelText, intent)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(context, channelId, name).also {
            builder.setChannelId(it.id)
        }
    }
    return builder.build()
}



/**
 * Create the required notification channel for O+ devices.
 */
@TargetApi(Build.VERSION_CODES.O)
fun createNotificationChannel(
    context: Context,
    channelId: String,
    name: String,
    notificationImportance: Int = NotificationManager.IMPORTANCE_HIGH
): NotificationChannel {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    return NotificationChannel(
        channelId, name, notificationImportance
    ).also { channel ->
        notificationManager.createNotificationChannel(channel)
    }
}
