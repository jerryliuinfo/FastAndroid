package com.tesla.framework.common.util;

import android.content.Context;
import android.os.Debug;
import android.view.InflateException;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 捕捉Crash日志本地文件保存，且OOM导出Hprof文件
 *
 * Created by JerryLiu on 17/4/08.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mDefHandler;

    private static CrashHandler mCrashHandler;
    private Context context;

    private CrashHandler(Context context) {
        this.context = context;
    }

    public static void setupCrashHandler(Context context) {
        // 如果初始化在Application中，防止初始化两次创建两个
        if (mCrashHandler != null) {
            return;
        }

        mCrashHandler = new CrashHandler(context);
        mCrashHandler.mDefHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(mCrashHandler);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (isOOM(ex)) {
            dumpHprof(context.getExternalFilesDir("logs").getAbsolutePath() + File.separator + "crash" + File.separator);
        }

        Logger.printExc(CrashHandler.class, ex);

        save2File(ex);

        if (mDefHandler != null) {
            mDefHandler.uncaughtException(thread, ex);
        }
    }

    private void save2File(Throwable ex) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);

            String crashLog = sw.toString();

            String filePath = context.getExternalFilesDir("logs").getAbsolutePath() + File.separator + "crash" + File.separator;
            File file = new File(filePath + File.separator + new SimpleDateFormat("yyyyMMdd_HH-mm-ss-SSS").format(Calendar.getInstance().getTime()) + ".txt");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(crashLog);
            fileWriter.flush();
            fileWriter.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static boolean isOOM(Throwable e) {
        int loopCount = 0;
        while (e != null && loopCount++ < 5) {
            if (isOOMInner(e)) {
                return true;
            }
            e = e.getCause();
        }
        return false;
    }

    private static boolean isOOMInner(Throwable e) {
        if (e == null) {
            return false;
        }
        return (e instanceof OutOfMemoryError) || (e instanceof InflateException);
    }


    private final static String OOM_SUFFIX = ".hprof";

    public static void dumpHprof(String path) {
        try {
            String name = getDate() + OOM_SUFFIX;
            path = path + File.separator + name;
            File file = path != null ? new File(path) : null;
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            Debug.dumpHprofData(path);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static String getDate() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
    }
}
