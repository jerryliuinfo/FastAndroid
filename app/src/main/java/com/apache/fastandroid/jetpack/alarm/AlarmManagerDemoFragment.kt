package com.apache.fastandroid.jetpack.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentAlarmManagerBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.util.Calendar


/**
 * Created by Jerry on 2023/12/26.
 * https://developer.android.com/develop/background-work/services/alarms?hl=zh-cn
 */
class AlarmManagerDemoFragment :
    BaseBindingFragment<FragmentAlarmManagerBinding>(FragmentAlarmManagerBinding::inflate) {

    private lateinit var context: Context
    private lateinit var alarmMgr: AlarmManager

    companion object {
        const val ACTION_ALARM = "com.apache.fastandroid.jetpack.alarm.ACTION_ALARM"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        context = requireContext()
        alarmMgr= requireContext().getSystemService(AlarmManager::class.java)

        mBinding.btnInaccuracyAlarm.setOnClickListener {
            inaccuracyAlarm()
        }

        mBinding.btnTimerAlarm.setOnClickListener {
            repeatingAlarm()
        }

        mBinding.btnPeriodicityAlarm.setOnClickListener {
            perioperiodicityAlarm()
        }

        mBinding.btnExactAlarm.setOnClickListener {
            exactlyAlarm()
        }

        mBinding.btnSetExactAndAllowWhileIdleAlarm.setOnClickListener {
            setExactAndAllowWhileIdleUsage()
        }

        mBinding.btnSetAlarmClock.setOnClickListener {
            setAlarmClockUsage()
        }

    }

    /**
     * 在未来某个确切时间调用闹钟。由于这些闹钟对用户来说非常显眼，因此系统绝不会调整其发送时间。
     * 系统会将这些闹钟识别为最关键的闹钟，并在必要时离开低功耗模式来触发闹钟。
     */
    private fun setAlarmClockUsage() {
        val pendingIntent = getPendingIntent()
        val info = AlarmClockInfo(SystemClock.elapsedRealtime() + 10 * 1000,pendingIntent)
        alarmMgr.setAlarmClock(info,pendingIntent)
    }

    /**
     * 在未来近乎精确的时间调用闹钟，即使采用了省电措施。
     *
     *
     */
    private fun setExactAndAllowWhileIdleUsage() {
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() + 10 * 1000, getPendingIntent())
    }


    /**
     * 周期性任务:设置一个每隔30分钟执行一次的任务
     */
    private fun perioperiodicityAlarm() {
        alarmMgr.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime(),
            // AlarmManager.INTERVAL_HALF_HOUR,
            10 * 1000,
            getPendingIntent()
        )

    }

    /**
     * 您的应用可以使用以下方法之一设置精确闹钟。这些方法已按顺序排列，因此更靠近列表底部的方法可以处理更耗时的任务，但需要更多系统资源。
     *
     *  在规定的时间精确的执行闹钟，比set方法设置的精度更高
     */
    private fun exactlyAlarm() {
        alarmMgr.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime() + 10 * 1000, getPendingIntent())
    }


    private var alarmReceiver: AlarmReceiver? = null

    private var mRegisted:Boolean = false

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onStart() {
        super.onStart()
        // 从Android9.0开始，系统不再支持静态广播，应用广播只能通过动态注册
        // 创建一个闹钟的广播接收器
        alarmReceiver = AlarmReceiver().apply {

            // 注册广播接收器，注册之后才能正常接收广播
            mRegisted = try {
                requireContext().registerReceiver(alarmReceiver, IntentFilter(ACTION_ALARM))
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    }

    override fun onStop() {
        super.onStop()
        alarmReceiver?.let {
            if (mRegisted){
                requireContext().unregisterReceiver(it)
            }
        }
    }

    /**
     * 不准确闹钟:在特定时间过后发出闹钟
     * 可能不需要精确闹钟的用例：
     * 1.在应用的整个生命周期内安排定时操作
     * Handler 类包含几种处理计时操作的好方法，例如在应用处于活动状态时，每 n 秒执行一些工作：postAtTime() 和 postDelayed()。
     * 请注意，这些 API 依赖于系统正常运行时间，而不是实时。
     *
     * 2.安排好的后台工作，例如更新应用和上传日志
     * 3.应在特定时间过后执行的用户指定操作（即使系统处于空闲状态）
     * 4.应在特定时间过后执行的用户指定操作，使用不精确闹钟。具体来说，就是调用 set()。
     * 5.可在指定时间范围内执行的用户指定操作：使用不精确闹钟。具体来说，就是调用 setWindow()。请注意，如果您的应用以 Android 12 或更高版本为目标平台，则允许的最短期限为 10 分钟。
     *
     *
     *
     *
     */
    private fun inaccuracyAlarm() {
        // 从系统服务中获取闹钟管理器
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            // 给当前时间加上若干秒
            add(Calendar.SECOND, 5)
        }
        // 开始设定闹钟，延迟若干秒后，携带延迟意图发送闹钟广播 set方法在api 19以后是不精确的闹钟
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, getPendingIntent())
    }

    /**
     * 定时提醒:设置一个每天早上8点的提醒
     */
    private fun repeatingAlarm() {

        // 从系统服务中获取闹钟管理器
        val alarmMgr = requireContext().getSystemService(AlarmManager::class.java)
        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        // 使用周期性的闹钟定时
        alarmMgr.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            getPendingIntent()
        )
    }


    private fun getPendingIntent():PendingIntent{
        // val intent = Intent(ACTION_ALARM)
        // 上面指定 Action 的方式在 Android O 以上会报  Background execution not allowed: receiving Intent
        val intent = Intent(requireContext(), AlarmReceiver::class.java).apply {
            action = ACTION_ALARM
        }
        // 创建一个用于广播的延迟意图
        val pIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        return pIntent
    }

}