package com.apache.fastandroid.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager.BadTokenException
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
import com.apache.fastandroid.component.anr.AnrConfig
import com.apache.fastandroid.component.anr.AnrManager
import com.apache.fastandroid.crash.Fabric.init
import com.apache.fastandroid.demo.blacktech.viewpump.CustomTextViewInterceptor
import com.apache.fastandroid.demo.blacktech.viewpump.TextUpdatingInterceptor
import com.apache.fastandroid.demo.component.loadsir.callback.*
import com.blankj.utilcode.util.*
import com.kingja.loadsir.core.LoadSir
import com.squareup.leakcanary.LeakCanary
import com.tencent.mmkv.MMKV
import com.tesla.framework.applike.FApplication
import com.tesla.framework.common.device.DeviceName
import com.tesla.framework.common.util.LaunchTimer
import com.tesla.framework.component.logger.AndroidLogAdapter
import com.tesla.framework.component.logger.DiskLogAdapter
import com.tesla.framework.component.logger.Logger
import com.wanjian.cockroach.Cockroach
import com.wanjian.cockroach.DebugSafeModeUI
import com.wanjian.cockroach.ExceptionHandler
import com.zwb.lib_base.utils.network.NetworkStateClient
import dagger.hilt.android.HiltAndroidApp
import dev.b3nedikt.viewpump.ViewPump.init
import timber.log.TimerLogger
import java.io.File
import kotlin.system.measureTimeMillis

/**
 * Created by jerryliu on 2017/3/26.
 */
@HiltAndroidApp
class FastApplication : Application(), ViewModelStoreOwner {

    private var mFactory: ViewModelProvider.Factory? = null

    private var mAppViewModelStore: ViewModelStore? = null

    private lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        val time = measureTimeMillis {
            context = this
            mAppViewModelStore = ViewModelStore()
            initAndroidUtil()
            initLog()
            initAop()
            initCrash()
            crashReport()
            //        initCockroach();
            initLoop()
            Logger.d("Application onCreate ")

            NetworkStateClient.register()


//        initTaskByTaskDispatcher();
//        initTaskByAppFaster();
            initTaskByStartup()
            //        initLoop();
            // data/data/com.apache.fastandroid/files/mmkv
            val rootDir = MMKV.initialize(this)
            Logger.d("mmkv rootDir:%s", rootDir)
            initBlockCancary()
            initAnr()
            initImageLoader()

            //traceview 开始检测
            // Debug.startMethodTracing("APP");

//        Lifecycle lifecycle = ProcessLifecycleOwner.get().getLifecycle();
//        lifecycle.addObserver(new ApplicationLifecycleObserverNew(ProcessLifecycleOwner.get()));

            //systrace 开始检测
//        TraceCompat.beginSection("trace");

            //初始化crash统计
            initHttp()
            initViewPump()
            LeakCanary.install(this)
            LaunchTimer.endRecord("Application end ")
            initLoadSir()
            DeviceName.init(this)
            initPermissionMonitor()
            initAppDress()
        }
        println("Application onCreate cost time: $time milles")
    }

    private fun initAop() {
//        XAOP.init(this);
//        XAOP.debug(true);
    }

    private fun initAndroidUtil() {
        Utils.init(FApplication.getApplication())
    }

    private fun initAppDress() {}
    private fun initPermissionMonitor() {
        try {
//            Class.forName("com.hua.permissionmonitor.PermissionMonitor");
//            PermissionMonitor.start(false);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initTaskByAppFaster() {

    }

    private fun initTaskByStartup() {

    }

    private fun initCockroach() {
        val sysExcepHandler = Thread.getDefaultUncaughtExceptionHandler()
        val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
        DebugSafeModeUI.init(this)
        Cockroach.install(this, object : ExceptionHandler() {
            override fun onUncaughtExceptionHappened(thread: Thread, throwable: Throwable) {
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:$thread<---", throwable)
                //                CrashLog.saveCrashLog(getApplicationContext(), throwable);
                Handler(Looper.getMainLooper()).post { ToastUtils.showShort("已经进入安全模式") }
            }

            override fun onBandageExceptionHappened(throwable: Throwable) {
                throwable.printStackTrace() //打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
                toast.setText("Cockroach Worked")
                toast.show()
            }

            override fun onEnterSafeMode() {
                ToastUtils.showShort("已经进入安全模式")
                DebugSafeModeUI.showSafeModeUI()
            }

            override fun onMayBeBlackScreen(e: Throwable) {
                val thread = Looper.getMainLooper().thread
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:$thread<---", e)
                //黑屏时建议直接杀死app
                sysExcepHandler.uncaughtException(thread, RuntimeException("black screen"))
            }
        })
    }

    private fun crashReport() {
//        CrashReport.initCrashReport(getApplicationContext(), "397713a129", false);
        init(this)
    }

    private fun initImageLoader() {
        /* String imageLoaderClassName = "";
        IImageLoaderstrategy loaderstrategy;
         if (!TextUtils.isEmpty(imageLoaderClassName)) {
            try {
                loaderstrategy = (IImageLoaderstrategy) Class.forName(imageLoaderClassName).newInstance();
            } catch (Exception e) {
                loaderstrategy = new GlideImageLoader();
            }
        } else {
             loaderstrategy = new GlideImageLoader();
         }
        ImageLoaderManager.getInstance().setImageLoaderStrategy(loaderstrategy);
        ImageLoaderManager.getInstance().init(FApplication.getContext());*/
    }

    private fun initBlockCancary() {
        Logger.d(TAG, "initBlockCancary --->")
        //        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

    private fun initCrash() {
        //Crash 日志
        CrashUtils.init(cacheDir) { Logger.d(TAG, "crash info: %s, e: %s") }
    }

    override fun attachBaseContext(base: Context) {
        Logger.d("Application attachBaseContext ")
        LaunchTimer.startRecord()
        MultiDex.install(base)
        //HotFixManager.loadDex(base);
        super.attachBaseContext(base)
    }

    /**
     * 全局请求的统一配置
     */
    private fun initHttp() {}
    private fun initLog() {
        Logger.addLogAdapter(DiskLogAdapter())
        Logger.addLogAdapter(AndroidLogAdapter())
        Logger.addLogAdapter(TimerLogger())
//        NLog.setDebug(true, com.tesla.framework.common.util.log.Logger.DEBUG)
    }

    private val logPath: String
        private get() {
            val dir = context!!.getExternalFilesDir("fastAndroid")!!
                .absolutePath + File.separator + "log"
            FileUtils.createOrExistsDir(dir)
            return dir
        }

    /** A tree which logs important information for crash reporting.  */
    private fun initCrashAndAnalysis() {
        //bugly统计
//        CrashReport.initCrashReport(getApplicationContext(),BuildConfig.BUGLY_APP_ID,BuildConfig.LOG_DEBUG);
//        //本地crash日志收集  使用bulgy时不能在本地手机日志
//        TUncaughtExceptionHandler.getInstance(getApplicationContext(),configCrashFilePath()).init(this, BuildConfig.DEBUG, false, 0, SplashActivity.class);
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //非默认值
        if (newConfig.fontScale != 1f) {
            resources
        }
        super.onConfigurationChanged(newConfig)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        if (res.configuration.fontScale != 1f) { //非默认值
            val newConfig = Configuration()
            newConfig.setToDefaults()
            //强制字体不随着系统改变而改变
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig)
            } else {
                res.updateConfiguration(newConfig, res.displayMetrics)
            }
        }
        return res
    }

    private fun initLoop() {
        ThreadUtils.runOnUiThread {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Exception) {
                    e.printStackTrace()
                    val stack = Log.getStackTraceString(e)
                    if (e is SecurityException) {
                    } else if (e is BadTokenException) {
                    } else if (e is IndexOutOfBoundsException) {
                    } else if (stack.contains("SelectionHandleView")
                        || stack.contains("Magnifier.show")
                        || stack.contains("ViewRootImpl.handleDragEvent")
                        || stack.contains("PinnedPopWindow.show")
                        || stack.contains("FloatingToolbar.show")
                        || stack.contains("Context.startForegroundService() did")
                        || stack.contains("BaseInputConnection")
                        || stack.contains("getTextBeforeCursor")
                        || stack.contains("startDragAndDrop")
                        || stack.contains("dispatchDragAndDrop")
                        || stack.contains("TextClassificationAsyncTask")
                        || stack.contains("startSelectionActionModeAsync")
                        || stack.contains("Editor.touchePositionIsInSelection")
                        || stack.contains("BlinkHandler.finishBlink")
                        || stack.contains("TextClassificationHelper")
                        || stack.contains("AppMeasurementService")
                        || stack.contains("SuggestionSPopupwindoW")
                        || stack.contains("ASynCinputstage")
                        || stack.contains("HandleView.updatePosition")
                        || stack.contains("onDialogDISmissed")
                        || stack.contains("com.oppo.intent.action.TRANSLATE")
                        || stack.contains("Accessibi1ityInteractionContro11er")
                        || stack.contains("BadTokenException")
                        || stack.contains("Service.startForeground()")
                        || stack.contains("com.swift.sandhook")
                        || stack.contains("updateForceDarkMode")
                        || stack.contains("ClipboardService")
                    ) {
                        e.printStackTrace()
                    } else {
                        e.printStackTrace()
                        //                            throw e;
                    }
                }
            }
        }
        Looper.getMainLooper().setMessageLogging {
            //                Logger.d(TAG, "message: "+ x);
        }
    }

    private fun initAnr() {
        val config = AnrConfig.with().set_timeoutInterval(2000)
            .setIgnoreDebugger(true)
            .setReportMainThreadOnly()
            .setAnrInterceptor { duration -> 3000 - duration }.build()
                AnrManager.getInstance().start(config);
    }

    private fun initViewPump() {
        init(TextUpdatingInterceptor(), CustomTextViewInterceptor())
    }

    override fun getApplicationContext(): Context {
//        return super.getApplicationContext();
        return this
    }

    override fun getViewModelStore(): ViewModelStore {
        return mAppViewModelStore!!
    }

    fun getAppViewModelProvider(activity: Activity): ViewModelProvider {
        return ViewModelProvider(
            activity.applicationContext as FastApplication,
            (activity.applicationContext as FastApplication).getAppFactory(activity)
        )
    }

    private fun getAppFactory(activity: Activity): ViewModelProvider.Factory {
        val application = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        }
        return mFactory as ViewModelProvider.Factory
    }

    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException("Your activity/fragment is not yet attached to " + "Application. You can't request ViewModel before onCreate call.")
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback()) //添加各种状态页
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .addCallback(TimeoutCallback())
            .addCallback(CustomCallback()) //当注册LoadSir 时如果设置了默认状态页，则会展示默认状态页，否则不展示
            .setDefaultCallback(LoadingCallback::class.java) //设置默认状态页
            .commit()
    }

    companion object {
        val TAG = FastApplication::class.java.simpleName

    }
}