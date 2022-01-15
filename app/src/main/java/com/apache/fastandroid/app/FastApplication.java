package com.apache.fastandroid.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.view.WindowManager;

import com.apache.fastandroid.component.anr.AnrConfig;
import com.apache.fastandroid.demo.blacktech.viewpump.CustomTextViewInterceptor;
import com.apache.fastandroid.demo.blacktech.viewpump.TextUpdatingInterceptor;
import com.apache.fastandroid.demo.component.loadsir.callback.CustomCallback;
import com.apache.fastandroid.demo.component.loadsir.callback.EmptyCallback;
import com.apache.fastandroid.demo.component.loadsir.callback.ErrorCallback;
import com.apache.fastandroid.demo.component.loadsir.callback.LoadingCallback;
import com.apache.fastandroid.demo.component.loadsir.callback.TimeoutCallback;
import com.apache.fastandroid.imageloader.GlideImageLoader;
import com.apache.fastandroid.jetpack.lifecycle.ApplicationLifecycleObserverNew;
import com.apache.fastandroid.performance.startup.faster.Task1New;
import com.apache.fastandroid.performance.startup.faster.Task2New;
import com.apache.fastandroid.performance.startup.faster.Task3New;
import com.apache.fastandroid.performance.startup.faster.Task4New;
import com.apache.fastandroid.performance.startup.startup.SDK1;
import com.apache.fastandroid.performance.startup.startup.SDK2;
import com.apache.fastandroid.performance.startup.startup.SDK3;
import com.apache.fastandroid.performance.startup.startup.SDK4;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.Utils;
import com.github.anrwatchdog.ANRWatchDog;
import com.kingja.loadsir.core.LoadSir;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mmkv.MMKV;
import com.tesla.framework.applike.FApplication;
import com.tesla.framework.common.device.DeviceName;
import com.tesla.framework.common.util.LaunchTimer;
import com.tesla.framework.component.imageloader.IImageLoaderstrategy;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.component.startup.Group;
import com.tesla.framework.component.startup.StartupManager;
import com.tesla.framework.component.startup.TimeListener;
import com.wxy.appstartfaster.dispatcher.AppStartTaskDispatcher;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.multidex.MultiDex;
import dev.b3nedikt.viewpump.ViewPump;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class FastApplication extends Application implements ViewModelStoreOwner {
    public static final String TAG = FastApplication.class.getSimpleName();

    private ViewModelStore mAppViewModelStore;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        long startTime = SystemClock.uptimeMillis();
        sContext = this;
        mAppViewModelStore = new ViewModelStore();
        initLog();
        Logger.d("Application onCreate ");
        Utils.init(FApplication.getApplication());

//        initTaskByTaskDispatcher();
//        initTaskByAppFaster();
        initTaskByStartup();
//        initLoop();
        FApplication.class.getSimpleName();
        // data/data/com.apache.fastandroid/files/mmkv
        String rootDir = MMKV.initialize(this);
        initAppLike();
        initBlockCancary();
        initAnr();
        initCrash();
        initImageLoader();

        //traceview 开始检测
       // Debug.startMethodTracing("APP");

        Lifecycle lifecycle = ProcessLifecycleOwner.get().getLifecycle();
        lifecycle.addObserver(new ApplicationLifecycleObserverNew(ProcessLifecycleOwner.get()));

        //systrace 开始检测
//        TraceCompat.beginSection("trace");

        //初始化crash统计
        initCrashAndAnalysis();
        initHttp();


        Logger.d(String.format("FastAndroidApplication onCreate cost time: %s ms", (SystemClock.uptimeMillis() - startTime)));
        initViewPump();
        LeakCanary.install(this);


        LaunchTimer.endRecord("Application end ");
        initLoadSir();
        DeviceName.init(this);

        initPermissionMonitor();

        initAppDress();
    }

    private void initAppDress() {

    }

    private void initPermissionMonitor() {
        try {
//            Class.forName("com.hua.permissionmonitor.PermissionMonitor");
//            PermissionMonitor.start(false);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void initTaskByAppFaster(){
        AppStartTaskDispatcher.getInstance()
                .setContext(this)
                .setShowLog(true)
                .setAllTaskWaitTimeOut(5000)
                .addAppStartTask(new Task2New())
                .addAppStartTask(new Task4New())
                .addAppStartTask(new Task3New())
                .addAppStartTask(new Task1New())
                .start()
                .await();
    }

    private void initTaskByStartup(){
        StartupManager.INSTANCE
                .addGroup(new Function1<Group, Unit>() {
                    @Override
                    public Unit invoke(Group group) {
                        group.add(new SDK1());
                        group.add(new SDK2());
                        return null;
                    }

                })
                .addGroup(new Function1<Group, Unit>() {
                    @Override
                    public Unit invoke(Group group) {
                        group.add(new SDK3());
                        group.add(new SDK4());
                        return null;
                    }
                })
                .cost(new TimeListener() {
                    @Override
                    public void itemCost(@NonNull String name, long time, @NonNull String threadName) {
                        com.orhanobut.logger.Logger.d(String.format("startup-itemCost:%s time: %s threadName:%s",name,time,threadName));

                    }

                    @Override
                    public void allCost(long time) {
                        com.orhanobut.logger.Logger.d(String.format("startup-allCost:%s ",time));

                    }
                })
                .start(this);

        ;

    }


    private void initImageLoader(){
        String imageLoaderClassName = "";
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
        ImageLoaderManager.getInstance().init(FApplication.getContext());
    }






    private void initBlockCancary() {
        Logger.d(TAG, "initBlockCancary --->");
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();


    }

    private void initCrash() {
        //Crash 日志
        CrashUtils.init(getCacheDir(), new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(CrashUtils.CrashInfo crashInfo) {
                Logger.d(TAG, "crash info: %s, e: %s");
            }
        });
    }


    @Override
    protected void attachBaseContext(Context base) {
        Logger.d("Application attachBaseContext ");
        LaunchTimer.startRecord();
        MultiDex.install(base);
        //HotFixManager.loadDex(base);
        super.attachBaseContext(base);
    }



    private void initAppLike(){

    }


    /**
     * 全局请求的统一配置
     */
    private void initHttp(){

    }


    private void initLog(){

        com.orhanobut.logger.Logger.addLogAdapter(new DiskLogAdapter());
    }

    private String getLogPath(){
        String dir =  getContext().getExternalFilesDir("fastAndroid").getAbsolutePath() + File.separator + "log";
        FileUtils.createOrExistsDir(dir);
        return dir;
    }



    private void initCrashAndAnalysis(){
        //bugly统计
//        CrashReport.initCrashReport(getApplicationContext(),BuildConfig.BUGLY_APP_ID,BuildConfig.LOG_DEBUG);
//        //本地crash日志收集  使用bulgy时不能在本地手机日志
//        TUncaughtExceptionHandler.getInstance(getApplicationContext(),configCrashFilePath()).init(this, BuildConfig.DEBUG, false, 0, SplashActivity.class);

    }



    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //非默认值
        if (newConfig.fontScale != 1){
            getResources();
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();
            //强制字体不随着系统改变而改变
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                createConfigurationContext(newConfig);
            } else {
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        return res;
    }


    private static Context sContext;

    public static Context getContext(){
        return sContext;
    }





    private void initLoop(){
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        Looper.loop();
                    }catch (Exception e){
                        e.printStackTrace();
                        String stack = Log.getStackTraceString(e);
                        if (e instanceof SecurityException){

                        }
                        else if (e instanceof WindowManager.BadTokenException){

                        }  else if (e instanceof IndexOutOfBoundsException){

                        }
                        else if (
                                stack.contains("Toast"))
                        {
                            e.printStackTrace();
                        }else {
                            throw e;
                        }
                    }

                }
            }
        });

        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
//                Logger.d(TAG, "message: "+ x);
            }
        });
    }

    private void initAnr(){
        AnrConfig config = AnrConfig.with().set_timeoutInterval(2000)
                .setIgnoreDebugger(true)
                .setReportMainThreadOnly()
                .setAnrInterceptor(new ANRWatchDog.ANRInterceptor() {

                    @Override
                    public long intercept(long duration) {
                        return 3000 - duration;
                    }
                }).build();
//        AnrManager.getInstance().start(config);
    }


    private void initViewPump(){
        ViewPump.init(new TextUpdatingInterceptor(), new CustomTextViewInterceptor());
    }

    @Override
    public Context getApplicationContext() {
//        return super.getApplicationContext();
        return this;
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    private void initLoadSir(){
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())//添加各种状态页
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .addCallback(new TimeoutCallback())
                .addCallback(new CustomCallback())
                //当注册LoadSir 时如果设置了默认状态页，则会展示默认状态页，否则不展示
                .setDefaultCallback(LoadingCallback.class)//设置默认状态页
                .commit();
    }
}


