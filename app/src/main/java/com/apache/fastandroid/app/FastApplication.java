package com.apache.fastandroid.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.WindowManager;

import com.apache.fastandroid.demo.blacktech.viewpump.CustomTextViewInterceptor;
import com.apache.fastandroid.demo.blacktech.viewpump.TextUpdatingInterceptor;
import com.apache.fastandroid.jetpack.lifecycle.ApplicationLifecycleObserverNew;
import com.apache.fastandroid.task.DBInitTask;
import com.apache.fastandroid.task.DoraemonkitTask;
import com.apache.fastandroid.task.ImageLoaderTask;
import com.apache.fastandroid.task.PerformanceTask;
import com.apache.fastandroid.util.MainLogUtil;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.FileUtils;
import com.optimize.performance.launchstarter.TaskDispatcher;
import com.squareup.leakcanary.LeakCanary;
import com.tcl.account.accountsync.bean.TclAccountBuilder;
import com.tcl.account.accountsync.bean.TclConfig;
import com.tencent.mmkv.MMKV;
import com.tesla.framework.applike.FApplication;
import com.tesla.framework.common.util.LaunchTimer;
import com.tesla.framework.common.util.handler.HandlerUtil;
import com.tesla.framework.common.util.log.Logger;
import com.tesla.framework.common.util.log.NLog;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.multidex.MultiDex;
import dev.b3nedikt.viewpump.ViewPump;

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
        sContext = this;
        mAppViewModelStore = new ViewModelStore();

        initLog();
        loop();
        FApplication.class.getSimpleName();
        // data/data/com.apache.fastandroid/files/mmkv
        String rootDir = MMKV.initialize(this);
        NLog.d(TAG, "rootDir: %s",rootDir);


        initAppLike();
        initBlockCancary();

        initCrash();
        MainLogUtil.d("Application onCreate ");
        long startTime = SystemClock.uptimeMillis();

        //traceview 开始检测
       // Debug.startMethodTracing("APP");

        Lifecycle lifecycle = ProcessLifecycleOwner.get().getLifecycle();
        lifecycle.addObserver(new ApplicationLifecycleObserverNew(ProcessLifecycleOwner.get()));


        //systrace 开始检测
//        TraceCompat.beginSection("trace");

        initTask2();

        //初始化crash统计
        initCrashAndAnalysis();
        initHttp();


        NLog.d(TAG, "FastAndroidApplication onCreate cost time: %s ms", (SystemClock.uptimeMillis() - startTime));
        initViewPump();
        LaunchTimer.endRecord("Application end ");

        TclConfig config = new TclConfig();
        //设置 appId, appIde 的值需向儿童教育 app 申请
        config.setAppId("46121610438946717");
        TclAccountBuilder.getInstance().init(config,this);
    }



    private void initTask2(){
        LaunchTimer.startRecord();
        TaskDispatcher.init(this);
        TaskDispatcher taskDispatcher = TaskDispatcher.createInstance();
        //DB初始化
        taskDispatcher.addTask(new DBInitTask());
        taskDispatcher.addTask(new DoraemonkitTask());

        taskDispatcher.addTask(new ImageLoaderTask());
        taskDispatcher.addTask(new PerformanceTask(this));
        taskDispatcher.start();
        LaunchTimer.endRecord("initTask2 end ");

    }







    private void initBlockCancary() {
        NLog.d(TAG, "initBlockCancary --->");
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();


    }

    private void initCrash() {
        //Crash 日志
        CrashUtils.init(getCacheDir(), new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(CrashUtils.CrashInfo crashInfo) {
                NLog.d(TAG, "crash info: %s, e: %s");
            }
        });
    }


    @Override
    protected void attachBaseContext(Context base) {
        MainLogUtil.d("Application attachBaseContext ");
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
        NLog.setDebug(true, Logger.VERBOSE);
        NLog.trace(Logger.TRACE_ALL, getLogPath() );
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





    private void loop(){
        HandlerUtil.getUIHandler().post(new Runnable() {
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
    }


    private void initViewPump(){
        ViewPump.init(new TextUpdatingInterceptor(), new CustomTextViewInterceptor());

    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }
}


