package com.apache.fastandroid

import android.app.Application
import android.content.Context
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.apache.fastandroid.app.FastApplication
import com.apache.fastandroid.component.once.Once
import com.apache.fastandroid.crash.Fabric
import com.apache.fastandroid.demo.blacktech.viewpump.CustomTextViewInterceptor
import com.apache.fastandroid.demo.blacktech.viewpump.TextUpdatingInterceptor
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.api.ApiHelperImpl
import com.apache.fastandroid.jetpack.flow.local.DatabaseBuilder
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelperImpl
import com.apache.fastandroid.jetpack.lifecycle.IAppStateListener
import com.apache.fastandroid.jetpack.lifecycle.TraditionalProcessLifecycleListener
import com.apache.fastandroid.network.api.ApiServiceFactory
import com.apache.fastandroid.util.Global
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
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.viewpump.ViewPump
import com.tesla.framework.performance.takt.Seat
import com.tesla.framework.performance.takt.Takt
import me.drakeet.floo.Floo
import me.drakeet.floo.Target
import java.io.File

/**
 * Created by Jerry on 2023/6/23.
 */
object ClientConfigurator {
    private var initialized = false

    private var mFactory: ViewModelProvider.Factory? = null

    private var mAppViewModelStore: ViewModelStore? = null

    lateinit var appSetting: AppSetting

    val apiHelper: ApiHelper by lazy {
        ApiHelperImpl(ApiServiceFactory.flowService)
    }
    lateinit var databaseHelper: DatabaseHelper


    @Synchronized
    fun init(application: Application) {
        if (initialized){
            return
        }
        initialized = true

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
        initLoop()
        Logger.d("Application onCreate ")

//            NetworkStateClient.register()

        initTaskByStartup()
        //        initLoop();
        // data/data/com.apache.fastandroid/files/mmkv
        initMMKV(context)


        initBlockCancary()
        initAnr()
        initImageLoader()

        //初始化crash统计
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
    }


    private fun initNetworkMonitor(context: Context) {
        OkNetworkMonitor.context = context

    }

    private fun initTakt(application: Application) {
        Takt.stock(application)
            .seat(Seat.TOP_RIGHT)
            .interval(250)
            .color(Color.WHITE)
            .size(24f)
            .alpha(.5f)
            .listener { fps -> Logger.d("Takt fps:$fps") }
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

    private fun initTaskByAppFaster() {

    }

    private fun initTaskByStartup() {

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
        //添加 Timer
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