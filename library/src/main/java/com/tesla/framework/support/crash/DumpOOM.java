package com.tesla.framework.support.crash;

import android.content.Context;
import android.os.Debug;
import android.view.InflateException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 01370340 on 2017/12/26.
 */

public class DumpOOM {

    public void dumpOOMFile(Context context, Throwable ex){
        if (isOOM(ex)) {
            File file = getOOMDumpFile(context);
            System.gc();

            try {
                Debug.dumpHprofData(file.getAbsolutePath());
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    private File getOOMDumpFile(Context mContext){
        File dir = new File(mContext.getExternalFilesDir(null) + File.separator + "hprof");
        if (!dir.exists()) {
            dir.mkdir();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String fileName = mContext.getPackageName() + "_" + format.format(new Date(System.currentTimeMillis()));
        File file = new File(dir, fileName + ".hprof");

        return file;
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

}
