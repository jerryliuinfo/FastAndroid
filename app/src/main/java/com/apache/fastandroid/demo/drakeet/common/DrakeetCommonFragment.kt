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
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.HandlerCompat
import com.apache.fastandroid.LogUtils
import com.apache.fastandroid.MainActivity
import com.apache.fastandroid.R
import com.apache.fastandroid.component.keyboard.KeyboardVisibilityDemoFragment
import com.apache.fastandroid.databinding.FragmentDrakeetKnowledgeBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.drakeet.RecycleviewStabledIdsFragment
import com.apache.fastandroid.demo.temp.bean.ReflectBean
import com.apache.fastandroid.jetpack.lifecycle.service.MyService
import com.apache.fastandroid.network.retrofit.OkHttpClientManager
import com.blankj.utilcode.util.ReflectUtils
import com.tesla.framework.common.device.DeviceName
import com.tesla.framework.common.util.AndroidVersion
import com.tesla.framework.common.util.CommonUtil
import com.tesla.framework.common.util.DrakeetUtils
import com.tesla.framework.common.util.DrakeetUtils.doOnMainThreadIdle
import com.tesla.framework.common.util.toast.ToastCompat
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.launchFragment
import com.tesla.framework.kt.showToast
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_drakeet_knowledge.*

/**
 * Created by Jerry on 2021/10/15.
 */
class DrakeetCommonFragment:BaseBindingFragment<FragmentDrakeetKnowledgeBinding>(FragmentDrakeetKnowledgeBinding::inflate) {

    companion object{
        private const val TAG = "DrakeetCommonFragment";
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        //https://wx.zsxq.com/dweb2/index/topic_detail/581111245444844

        mBinding.btnToast.setOnClickListener {
            ToastCompat.makeText(context, "i am toast",ToastCompat.LENGTH_SHORT).show()
        }
        mBinding.btnHookContext.setOnClickListener {
            hookContextTest()
        }

        mBinding.btnJudgeSdkVersion.setOnClickListener {
            AndroidVersion.isAndroid12()
        }

        mBinding.btnServiceOntaskRemoved.setOnClickListener {
            context?.let { it1 -> MyService.start(it1) }
        }


        mBinding.btnSamsungNotificationBug.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                showNotification()
            }
        }

        mBinding.btnChekcsdkIntAtLeast.setOnClickListener {
            CommonUtil.fromApi(26) {
                println("api above 26, do something")
            }
            isAtLeastO()
        }
        mBinding.btnDoOnMainThreadIdle.setOnClickListener {
            doOnMainThreadIdle({
            },4000)

        }
        btn_string_hash_conflict.setOnClickListener {
        }
        var i = 0;
        mBinding.btnPrintCallChain.setOnClickListener {
            i++
            if (i > 3){
                i = 0;
                var stackTrace = DrakeetUtils.stackTrace(null, 10)
            }
        }
        mBinding.btnGetDeviceName.setOnClickListener {
            //https://t.zsxq.com/bE2znqV
            kotlin.runCatching {
                val deviceName = DeviceName.getDeviceName("clark", "Unknown device")
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
        mBinding.btnLaunchMode.setOnClickListener {

        }
        mBinding.btnRestart.setOnClickListener {
            DrakeetUtils.triggerRestart(requireContext(),null)
        }
        /**
         * https://t.zsxq.com/6mI2jU3
         * 从API28开始，Handler类增加了静态方法createAsyn。主要作用是使所有通过这个Handler发送的Message，
         * 都会被设置为FLAG_ASYNCHRONOUS异步消息(默认是同步消息)，在搭配消息屏障使用的情况下，会被优先调用
         */
        mBinding.btnAsyncHandler.setOnClickListener {
            if (Build.VERSION.SDK_INT >= 28){
                HandlerCompat.createAsync(Looper.myLooper()!!).postDelayed({
                    println("post delay with async handler")
                },500)
            }

        }

        //https://t.zsxq.com/eeIeU3n
        mBinding.btnReflect.setOnClickListener {
            ReflectUtils.reflect(ReflectBean::class.java).field("sCacheList", arrayListOf("Jerry","Tom"))
            val sCacheList:List<String> = ReflectUtils.reflect(ReflectBean::class.java).field("sCacheList").get<List<String> >()
            Logger.d("length:${sCacheList}")
        }


        Logger.d("context file dir:${requireContext().filesDir}, cache:${requireContext().cacheDir}, " +
                "externalFileDir:${requireContext().getExternalFilesDir(null)}, externalCacheDir:${requireContext().externalCacheDir}",
        )

        mBinding.btnAdbScreenSize.setOnClickListener {
            modifyScreenSizeByAdb()
        }

        mBinding.btnNetworkSecurity.setOnClickListener {
            networkSecurity()
        }

        mBinding.btnSdkEditor.setOnClickListener {
            sdkEditorTest()
        }
        mBinding.btnStableId.setOnClickListener {
            requireActivity().launchFragment<RecycleviewStabledIdsFragment>()
        }


    }

    private fun checkSdkInAtLeast() {
        @ChecksSdkIntAtLeast
        if (Build.VERSION.SDK_INT >= CommonUtil.ANROID_O) {

        }
    }


    /**
     * 源自:https://t.zsxq.com/nuJqFY3
     * 帮助lint识别间接的 DK_INT 检查，在此之前lint有时很愚蠢，它大多数情况下只能识别
     * 最直接的写法，比如 Build.VERSION.SDK_INT >= Build.VERSION_CODES.O， 但无法识别间接封装的条件，
     * 即便内容是一样的，这引起的麻烦是我们不得不重复手写  Build.VERSION.SDK_INT >= Build.VERSION_CODES.O 代码
     *
     */
    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
    fun isAtLeastO(): Boolean {
        //26
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }


    var str = "hello_world"

    /**
     * https://wx.zsxq.com/dweb2/index/topic_detail/118844454482222
     */
    private fun sdkEditorTest() {
        val list = listOf<String>("hello", "world",)
    }

    private fun networkSecurity() {
        val okHttpClient = OkHttpClientManager.getOkHttpClient()
        Logger.d("okHttpClient:$okHttpClient")
    }

    /**
     * https://wx.zsxq.com/dweb2/index/topic_detail/582151444451454
     */
    private fun modifyScreenSizeByAdb() {
       //查看设备尺寸  adb shell wm size

        //修改尺寸 adb shell wm size  1080x2220

    }

    /**
     * https://wx.zsxq.com/dweb2/index/topic_detail/581111245444844
     */
    private fun hookContextTest() {
        val str = requireContext().getString(R.string.text_hook)
        showToast("hookd text:$str")
    }

    /**
     * https://t.zsxq.com/Nvzjyn2  三星 Android 10 系统通知 bug
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

//    override fun getContext(): Context {
//        return HookContext(super.requireContext())
//    }

}