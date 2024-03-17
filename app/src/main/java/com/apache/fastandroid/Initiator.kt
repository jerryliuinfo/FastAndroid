package com.apache.fastandroid

import SDK2
import SDK3
import SDK4
import SDK5
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Debug
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.apache.fastandroid.app.FastApplication
import com.apache.fastandroid.component.once.Once
import com.apache.fastandroid.crash.Fabric
import com.apache.fastandroid.demo.blacktech.viewpump.CustomTextViewInterceptor
import com.apache.fastandroid.demo.blacktech.viewpump.TextUpdatingInterceptor
import com.apache.fastandroid.demo.performance.startup.SDK1
import com.apache.fastandroid.demo.performance.taskdispatcher.TestAppStartTaskFive
import com.apache.fastandroid.demo.performance.taskdispatcher.TestAppStartTaskFour
import com.apache.fastandroid.demo.performance.taskdispatcher.TestAppStartTaskOne
import com.apache.fastandroid.demo.performance.taskdispatcher.TestAppStartTaskThree
import com.apache.fastandroid.demo.performance.taskdispatcher.TestAppStartTaskTwo
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.api.ApiHelperImpl
import com.apache.fastandroid.jetpack.flow.local.DatabaseBuilder
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelperImpl
import com.apache.fastandroid.jetpack.lifecycle.IAppStateListener
import com.apache.fastandroid.jetpack.lifecycle.TraditionalProcessLifecycleListener
import com.apache.fastandroid.network.api.ApiServiceFactory
import com.apache.fastandroid.util.Global
import com.apache.fastandroid.util.MultidexUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.Utils
import com.linkaipeng.oknetworkmonitor.OkNetworkMonitor
import com.squareup.leakcanary.LeakCanary
import com.tencent.mmkv.MMKV
import com.tesla.framework.applike.FApplication
import com.tesla.framework.common.device.DeviceName
import com.tesla.framework.common.util.LaunchTimer
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.component.logger.adapter.AndroidLogAdapter
import com.tesla.framework.component.logger.format.SimpleFormatStrategy
import com.tesla.framework.component.startup.StartupManager
import com.tesla.framework.component.startup.TimeListener
import com.tesla.framework.performance.takt.Seat
import com.tesla.framework.performance.takt.Takt
import com.wxy.appstartfaster.dispatcher.AppStartTaskDispatcher
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.viewpump.ViewPump
import me.drakeet.floo.Floo
import me.drakeet.floo.Target
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Jerry on 2023/6/23.
 */
object Initiator {
    private var initialized = false

    private var mFactory: ViewModelProvider.Factory? = null

    private var mAppViewModelStore: ViewModelStore? = null

    lateinit var appSetting: AppSetting

    val apiHelper: ApiHelper by lazy {
        ApiHelperImpl(ApiServiceFactory.flowService)
    }
    lateinit var databaseHelper: DatabaseHelper
    lateinit var sharedPreferences: SharedPreferences

    private const val KEY_SCHEMA_VERSION = "schema_version"
    private const val SCHEMA_VERSION = 2023022701


    @Synchronized
    fun init(application: Application) {
        if (initialized) {
            return
        }
        val dateFormat: DateFormat = SimpleDateFormat("dd_MM_yyyy_hh", Locale.getDefault())
        val logDate: String = dateFormat.format(Date())
        // Debug.startMethodTracing("FastAndroid-$logDate")
        initialized = true
        initLoop()

        val context = application.applicationContext

        Utils.init(application)

        initNightMode(context)
        databaseHelper =
            DatabaseHelperImpl(DatabaseBuilder.getInstance(application.applicationContext))
        mAppViewModelStore = ViewModelStore()
        initAndroidUtil()
        initLog()
        initOnce(context)
        initAop()
        crashReport(application)
        initTakt(application)
        Logger.d("Application onCreate ")

//            NetworkStateClient.register()


        initAppFaster(context)

        initTaskByStartup(context)
        //        initLoop();
        // data/data/com.apache.fastandroid/files/mmkv
        initMMKV(context)


        initBlockCancary()
        initAnr()
        initImageLoader()

        // 初始化crash统计
        initHttp()
        initViewPump()
        initReword(context)
        LeakCanary.install(application)
        LaunchTimer.endRecord("Application end ")
        initLoadSir()
        DeviceName.init(application.applicationContext)
        initPermissionMonitor()
        initAppDress()
        initFloo()
        initBooster()

        Utils.getApp()
            .registerActivityLifecycleCallbacks(TraditionalProcessLifecycleListener(object :
                IAppStateListener {
                override fun onAppForeground() {
                    Logger.d("onAppForeground -->")
                }

                override fun onAppBackground() {
                    Logger.d("onAppForeground -->")
                }

            }))
        initNetworkMonitor(context)

        sharedPreferences = Utils.getApp().getSharedPreferences("config", Context.MODE_PRIVATE)

        val oldVersion = sharedPreferences.getInt(KEY_SCHEMA_VERSION, 0)
        if (oldVersion != SCHEMA_VERSION) {
            upgradeSharedPreferences(oldVersion, SCHEMA_VERSION)
        }
        initWorkManager(application)
        Debug.stopMethodTracing()
    }

    private fun initWorkManager(context: Context) {
        // WorkManager.initialize(
        //     context,
        //     Configuration.Builder().setExecutor(Executors.newFixedThreadPool(8)).build()
        // )
    }

    private fun upgradeSharedPreferences(oldVersion: Int, newVersion: Int) {
        Logger.d("Upgrading shared preferences: $oldVersion -> $newVersion")
//        val editor = sharedPreferences.edit()
        val editor = Utils.getApp().getSharedPreferences("config", Context.MODE_PRIVATE).edit()


        if (oldVersion < 2023022701) {
            // These preferences are (now) handled in AccountPreferenceHandler. Remove them from shared for clarity.

            editor.remove("oldkey1")
            editor.remove("oldkey2")
        }

        editor.putInt(KEY_SCHEMA_VERSION, newVersion)
        editor.apply()
    }

    private fun initBooster() {
//        FinalizerWatchdogDaemonKiller.

    }

    private fun initNetworkMonitor(context: Context) {
        OkNetworkMonitor.context = context

    }

    private fun initAppFaster(context: Context) {
        if (MultidexUtils.isMainProcess(context)) {
            AppStartTaskDispatcher.getInstance()
                .setContext(context)
                .setShowLog(true)
                .setAllTaskWaitTimeOut(1000)
                .addAppStartTask(TestAppStartTaskTwo())
                .addAppStartTask(TestAppStartTaskFour())
                .addAppStartTask(TestAppStartTaskFive())
                .addAppStartTask(TestAppStartTaskThree())
                .addAppStartTask(TestAppStartTaskOne())
                .start()
                .await()
        }
    }

    private fun initTakt(application: Application) {
        Takt.stock(application)
            .seat(Seat.TOP_RIGHT)
            .interval(250)
            .color(Color.WHITE)
            .size(24f)
            .alpha(.5f)
            .listener { fps ->
//                Logger.d("Takt fps:$fps")
            }
            .useCustomControl()
    }

    private fun initNightMode(context: Context) {
        appSetting = AppSetting(
            context.getSharedPreferences(
                FastApplication.DEFAULT_PREFERENCES,
                Context.MODE_PRIVATE
            )
        )
    }

    private fun initOnce(context: Context) {
        Once.initialise(context)
    }

    private fun initMMKV(context: Context) {
        val rootDir = MMKV.initialize(context)
        Logger.d("mmkv rootDir:%s", rootDir)
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


    private fun initTaskByStartup(context: Context) {
        StartupManager
            .addGroup {
                it.add(SDK1())
                it.add(SDK2())
            }
            .addGroup {
                it.add(SDK3())
                it.add(SDK4())
                it.add(SDK5())
            }
            .cost(object : TimeListener {

                override fun itemCost(name: String, time: Long, threadName: String) {
                    Log.d("startup-", "itemCost:$name time:$time threadName:$threadName")
                }

                override fun allCost(time: Long) {
                    Log.d("startup-", "allCost:$time")
                }
            })
            .start(context)
    }

    private fun crashReport(application: Application) {
        Fabric.init(application)
//        initCockroach()
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
        Logger.d(FastApplication.TAG, "initBlockCancary --->")
        //        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }


    /**
     * 全局请求的统一配置
     */
    private fun initHttp() {}
    private fun initLog() {
//        Logger.addLogAdapter(DiskLogAdapter())
        Logger.addLogAdapter(AndroidLogAdapter(SimpleFormatStrategy.newBuilder().build()))
        // 添加 Timer
//        Logger.addLogAdapter(TimerLogger())
    }

    private val logPath: String
        private get() {
            val dir = FastApplication.context!!.getExternalFilesDir("fastAndroid")!!
                .absolutePath + File.separator + "log"
            FileUtils.createOrExistsDir(dir)
            return dir
        }


    private fun initLoop() {
        Global.start()
    }

    private fun initAnr() {
        /*val config = AnrConfig.with().set_timeoutInterval(2000)
            .setIgnoreDebugger(true)
            .setReportMainThreadOnly()
            .setAnrInterceptor { duration -> 3000 - duration }.build()
                AnrManager.getInstance().start(config);*/
    }

    private fun initReword(context: Context) {
        Restring.init(context)
    }

    private fun initViewPump() {
        ViewPump.init(TextUpdatingInterceptor(), CustomTextViewInterceptor())
    }


    private fun initLoadSir() {
//        LoadSir.beginBuilder()
//            .addCallback(ErrorCallback()) //添加各种状态页
//            .addCallback(Callback.EmptyCallback())
//            .addCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.LoadingCallback())
//            .addCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.TimeoutCallback())
//            .addCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.CustomCallback()) //当注册LoadSir 时如果设置了默认状态页，则会展示默认状态页，否则不展示
//            .setDefaultCallback(com.apache.fastandroid.demo.component.loadsir.sample.callback.LoadingCallback::class.java) //设置默认状态页
//            .commit()
    }


    private fun initFloo() {
        val mappings: MutableMap<String, Target> = HashMap()
        mappings["m.drakeet.me/home"] = Target("floo://drakeet.sdk/target")
        mappings["m.drakeet.me/link"] = Target("floo://drakeet.sdk/target")
        mappings["m.drakeet.me/web"] = Target("floo://drakeet.sdk/web")
        mappings["m.drakeet.me/container"] = Target("floo://m.drakeet.me/container")
        mappings["mosaic.chunchun.io:8080"] =
            Target("https://play.google.com/store/apps/details?id=me.drakeet.puremosaic")
        mappings["PureWriter"] =
            Target("https://play.google.com/store/apps/details?id=com.drakeet.purewriter")


        Floo.apply(mappings)
    }
}