package com.apache.fastandroid.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;

import com.apache.fastandroid.BuildConfig;
import com.apache.fastandroid.aop.track.TrackPoint;
import com.apache.fastandroid.aop.track.TrackPointCallBack;
import com.apache.fastandroid.artemis.BaseApp;
import com.apache.fastandroid.artemis.constant.AppConfig;
import com.apache.fastandroid.artemis.http.GlobalHttp;
import com.apache.fastandroid.artemis.support.bean.OAuth;
import com.apache.fastandroid.artemis.util.BaseLibLogUtil;
import com.apache.fastandroid.imageloader.GlideImageLoader;
import com.apache.fastandroid.jetpack.lifecycle.ApplicationLifecycleObserverNew;
import com.apache.fastandroid.task.DBInitTask;
import com.apache.fastandroid.task.DoraemonkitTask;
import com.apache.fastandroid.task.ImageLoaderTask;
import com.apache.fastandroid.task.PerformanceTask;
import com.apache.fastandroid.util.LogDelegate;
import com.apache.fastandroid.util.MainLogUtil;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;
import com.optimize.performance.launchstarter.TaskDispatcher;
import com.squareup.leakcanary.LeakCanary;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.applike.IApplicationLike;
import com.tesla.framework.common.setting.SettingUtility;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.common.util.log.FastLog.LogConfig;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.common.util.sp.SPUtil;
import com.tesla.framework.component.imageloader.IImageLoaderstrategy;
import com.tesla.framework.component.performance.BlockDetector;

import java.io.File;

import androidx.core.os.TraceCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;

/**
 * Created by jerryliu on 2017/3/26.
 */

public class FastApplication extends FrameworkApplication {
    public static final String TAG = FastApplication.class.getSimpleName();

    private static final String client_id = "7024a413";
    private static final String client_secret = "8404fa33ae48d3014cfa89deaa674e4cbe6ec894a57dbef4e40d083dbbaa5cf4";

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        sContext = this;
        sApplication = this;
        initLog();
        BaseApp.onCreate(this);
        initAppLike();
//        initAnrWatchDog();
        initBlockCancary();
        initCrash();
        MainLogUtil.d("Application onCreate ");
        long startTime = SystemClock.uptimeMillis();

        //traceview 开始检测
       // Debug.startMethodTracing("APP");

        Lifecycle lifecycle = ProcessLifecycleOwner.get().getLifecycle();
        lifecycle.addObserver(new ApplicationLifecycleObserverNew(ProcessLifecycleOwner.get()));


        //systrace 开始检测
        TraceCompat.beginSection("trace");

        TaskDispatcher.init(this);
        TaskDispatcher taskDispatcher = TaskDispatcher.createInstance();
        taskDispatcher.addTask(new DoraemonkitTask());
        //DB初始化
        taskDispatcher.addTask(new DBInitTask());
        taskDispatcher.addTask(new ImageLoaderTask());
        taskDispatcher.addTask(new PerformanceTask(this));
        taskDispatcher.start();


        SPUtil.init(getApplicationContext(),getPackageName() +"_share");

        //初始化crash统计
        initCrashAndAnalysis();



        initAuth();
        initHttp();
        if (AppUtils.isAppDebug()){
            BlockDetector.init();
        }
        TrackPoint.init(new TrackPointCallBack() {
            @Override
            public void onClick(String pageClassName, String viewIdName) {
                MainLogUtil.d("onClick: " + pageClassName + "-" + viewIdName);
                //添加你的操作
            }

            @Override
            public void onPageOpen(String pageClassName) {
                BaseLibLogUtil.d("onPageOpen: " + pageClassName);
                //添加你的操作
            }

            @Override
            public void onPageClose(String pageClassName) {
                MainLogUtil.d("onPageClose: " + pageClassName);
                //添加你的操作
            }
        });
        //traceview 结束检测
       // Debug.stopMethodTracing();

        //systrace 结束检测
        TraceCompat.endSection();


        NLog.d(TAG, "FastAndroidApplication onCreate cost time: %s ms", (SystemClock.uptimeMillis() - startTime));

    }

    public ANRWatchDog anrWatchDog = new ANRWatchDog(5000);

    int duration = 4;

     public ANRWatchDog.ANRListener silentListener = new ANRWatchDog.ANRListener() {
        @Override
        public void onAppNotResponding(ANRError error) {
            Log.e("ANR-Watchdog-Demo", "", error);

        }
    };

    public void initAnrWatchDog() {
        NLog.d(TAG, "initAnrWatchDog --->");
        anrWatchDog
                .setANRListener(silentListener)
                .setANRInterceptor(new ANRWatchDog.ANRInterceptor() {
                    @Override
                    public long intercept(long duration) {
                        long ret = FastApplication.this.duration * 1000 - duration;
                        if (ret > 0)
                            Log.w("ANR-Watchdog-Demo", "Intercepted ANR that is too short (" + duration + " ms), postponing for " + ret + " ms.");
                        return ret;
                    }
                })
        ;

        anrWatchDog.start();
    }

    private void initBlockCancary() {
        NLog.d(TAG, "initBlockCancary --->");
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

    private void initCrash() {
        //Crash 日志
        CrashUtils.init(getCacheDir(), new CrashUtils.OnCrashListener() {
            @Override
            public void onCrash(String crashInfo, Throwable e) {
                NLog.d(TAG, "crash info: %s, e: %s", crashInfo,e.getMessage());
            }
        });
    }


    @Override
    protected void attachBaseContext(Context base) {
        MainLogUtil.d("Application attachBaseContext ");
        MultiDex.install(base);
        //HotFixManager.loadDex(base);
        super.attachBaseContext(base);
    }



    private void initAppLike(){
        for (String componentAppName: AppConfig.COMPONENT_APPLICATION_CONFIG) {
            try {
                Class<?> clz = Class.forName(componentAppName);
                IApplicationLike applicationLike  = (IApplicationLike) clz.newInstance();
                applicationLike.onCreate(this);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }


    /**
     * 全局请求的统一配置
     */
    private void initHttp(){
        GlobalHttp.setBaseUrl("https://api.douban.com/")
                //开启缓存策略
                .setCache().setCookie(false).setSslSocketFactory()
                //使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(getAssets().open("your.bks"), "123456", getAssets().open("your.cer"))
                .setReadTimeout(10)
                .setWriteTimeout(10)
                .setConnectTimeout(10)
                .setLog(true);
    }


    private void initLog(){
        LogDelegate logDelegate = new LogDelegate();
        LogConfig config = new LogConfig();
        config.openLog = BuildConfig.DEBUG;
        logDelegate.setLogConfig(config);
        FastLog.setLogDelegate(logDelegate);
    }



    private void initCrashAndAnalysis(){
        //bugly统计
//        CrashReport.initCrashReport(getApplicationContext(),BuildConfig.BUGLY_APP_ID,BuildConfig.LOG_DEBUG);
//        //本地crash日志收集  使用bulgy时不能在本地手机日志
//        TUncaughtExceptionHandler.getInstance(getApplicationContext(),configCrashFilePath()).init(this, BuildConfig.DEBUG, false, 0, SplashActivity.class);

    }

    private String configCrashFilePath(){
        String path = "";
        try {
            String storageStr = Environment.getExternalStorageState();
            if (storageStr != null && storageStr.equals(Environment.MEDIA_MOUNTED)) {
                File file = this.getExternalFilesDir(null);
                if (file != null) {
                    path = file + File.separator + "crash"; // Android/data/pkgName/files/crash
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
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


    private void initAuth(){
        OAuth.client_id = client_id;
        OAuth.client_secret = client_secret;
    }

    private IImageLoaderstrategy configImageLoader(){
        String imageLoaderClassName = SettingUtility.getStringSetting("imageLoader");
        if (!TextUtils.isEmpty(imageLoaderClassName)){
            try {
                return (IImageLoaderstrategy) Class.forName(imageLoaderClassName).newInstance();
            }catch (Exception e){
                return new GlideImageLoader();
            }
        }
        //如果没有配置，默认使用glide加载
        return new GlideImageLoader();
    }


    private static Context sContext;

    private static Application sApplication;

    public static Context getContext(){
        return sContext;
    }

    public static FastApplication getApplication(){
        return (FastApplication) sApplication;
    }


}


