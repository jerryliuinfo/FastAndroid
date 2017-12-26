package com.tesla.framework.support.crash;

import com.tesla.framework.common.util.log.NLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 01370340 on 2017/12/26.
 */

public class SaveCrashLog implements ISaveCrashFile {
    @Override
    public boolean saveFile(Thread thread, Throwable ex,String dir, String crashInfo) {
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        printWriter.append(crashInfo);

        printWriter.append("*********************************************************************************\n");
        if (thread != null) {
            String name = String.format("%s(%d)", thread.getName(), thread.getId());
            printWriter.write("the crashed thread: ");
            printWriter.write(name);
            printWriter.write("\n");
        }

        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();

        // 用于格式化日期,作为日志文件名的一部分
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(getCrashFullNameFilePath(dir));
            fos.write(writer.toString().getBytes("utf-8"));
            return true;
        } catch (Exception e) {
            NLog.printStackTrace(e);
            return false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    NLog.printStackTrace(e);
                }
            }
        }
    }


    /**
     * crash日志文件全路径
     * @param dirPath
     * @return
     */
    private String getCrashFullNameFilePath(String dirPath){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String time = formatter.format(new Date());
        String fileName = "crash-" + time + ".txt";
        return dirPath + File.separator + fileName;
    }



}
