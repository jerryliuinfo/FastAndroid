package com.tesla.framework.support.crash;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tesla.framework.common.util.ActivityTaskMgr;
import com.tesla.framework.common.util.log.NLog;

import java.io.File;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TUncaughtExceptionHandler implements UncaughtExceptionHandler {

    private static final String TAG = "ExceptionHandler";

    private UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;
    // 用来存储设备信息和异常信息
    private String mDeviceInfos;
    private String mCrashDirPath;


    //是否是Debug模式
    private boolean mIsDebug;
    //是否重启APP
    private boolean mIsRestartApp; // 默认需要重启
    //重启APP时间
    private long mDelayRestartTime;

    // 重启后跳转的Activity
    private Class mRestartActivity;

    // Toast 显示文案
    private String mTips;

    private ISaveCrashFile iSaveCrashFile;




    private static TUncaughtExceptionHandler instance = null;
    private TUncaughtExceptionHandler(Context context, String path){
        this.mContext = context;
        this.mCrashDirPath = path;
        iSaveCrashFile = new SaveCrashLog();
        collectDeviceInfo();
    }
    public static TUncaughtExceptionHandler getInstance(Context context, String path) {
        if (instance == null) {
            synchronized (TUncaughtExceptionHandler.class) {
                if (instance == null){
                    instance = new TUncaughtExceptionHandler(context,path);
                }
            }
        }
        return instance;
    }


    public void init(Context context, boolean isDebug, boolean isRestartApp, long restartTime, Class restartActivity) {
        this.mIsRestartApp = isRestartApp;
        this.mDelayRestartTime = restartTime;
        this.mRestartActivity = restartActivity;
        this.init(context, isDebug);
    }

    public void init(Context context, boolean isDebug) {
        this.mTips = "很抱歉，程序出现异常，即将退出...";
        this.mIsDebug = isDebug;
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }




    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    protected boolean handleException(Thread thread, Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();
//        collectMemInfo();
        // 保存日志文件

        // 使用 Toast 来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "我崩溃啦~~~~~~~", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        saveCrashInfo2File(thread, ex);
        return true;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        NLog.d(TAG, "-------->uncaughtException thread = %s, ex = %s",thread, ex);
        if (mIsDebug) {
            new DumpOOM().dumpOOMFile(mContext,ex);
        }
        if (!handleException(thread, ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //杀死该应用进程
            ActivityTaskMgr.getInstance().clearActivityStack();

            if (mIsRestartApp) { // 如果需要重启
                Intent intent = new Intent(mContext.getApplicationContext(), mRestartActivity);
                AlarmManager mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                //重启应用，得使用PendingIntent
                /*PendingIntent restartIntent = PendingIntent.getActivity(
                        mContext.getApplicationContext(), 0, intent,
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                mAlarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + mDelayRestartTime,
                        restartIntent); // 重启应用*/
            }

            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /**
     * 收集设备参数信息
     */
    private void collectDeviceInfo() {
        if(TextUtils.isEmpty(mCrashDirPath)){
            return;
        }

        StringWriter deviceInfos = new StringWriter();
        try {
            deviceInfos.append("=================================================================================\n");
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;

                deviceInfos.append("versionName=").append(versionName).append("\n");
                deviceInfos.append("versionCode=").append(pi.versionCode + "").append("\n");
            }
        } catch (Exception e) {
        }

        deviceInfos.append("androidVersion=").append(Build.VERSION.RELEASE).append("\n");

        deviceInfos.append("=================================================================================\n");
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                deviceInfos.append(field.getName()).append("=").append(field.get(null).toString()).append("\n"); // TODO
            } catch (Exception e) {
                Log.e(TAG, " collectDeviceInfo Exception" + e);
            }
        }

        mDeviceInfos = deviceInfos.toString();
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private boolean saveCrashInfo2File(Thread thread, Throwable ex) {
        String crashDirPath = getCrashLogDirPath();
        if (TextUtils.isEmpty(crashDirPath)){
            return false;
        }

        //保存崩溃日志到本地
        if (iSaveCrashFile != null){
            return new SaveCrashLog().saveFile(thread,ex,crashDirPath,mDeviceInfos);
        }
        return false;
    }

    /**
     * crash日志路径
     * @return
     */
    private String getCrashLogDirPath(){
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return null;
        }
        String dirForCrashFile = mCrashDirPath;
        if (TextUtils.isEmpty(mCrashDirPath)) {
            dirForCrashFile = mContext.getExternalFilesDir(null) + File.separator + "crash";
        }
        // 检查目录是否存在，不存在则创建
        File dir = new File(dirForCrashFile);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirForCrashFile;
    }


    private File getOOMDumpFile(){
        File dir = new File(mContext.getExternalFilesDir(null) + File.separator + "hprof");
        if (!dir.exists()) {
            dir.mkdir();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = mContext.getPackageName() + "_" + format.format(new Date(System.currentTimeMillis()));
        File file = new File(dir, fileName + ".hprof");

        return file;
    }
}
