package com.coco.aspectjlib;

import android.content.Context;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static Context mContext = null;
    /**
     * 保存日志到文件
     * todo 上传机制可以当文件大小超过某一值或者连续写入多少次之后
     * @param tag 日志标签
     * @param value 具体内容
     */
    public static void saveInfo(String tag, String value) {
        FileWriter fw = null;
        File file = new File(mContext.getFilesDir(), "info.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            // true--追加写入
            fw = new FileWriter(file,true);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            fw.write(df.format(new Date()) + "  " + tag + "   " + value + "\r\n");   //向文件中写内容
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取文件内容，测试用
     *
     * @param file
     * @return
     */
    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));    //构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {

                //使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 获取 view 参数
     * @param args
     * @return
     */
    public static View getViewFromArgs(Object[] args) {
        if (args != null && args.length > 0) {
            Object arg = args[0];
            if (arg instanceof View) {
                return (View) arg;
            }
        }
        return null;
    }
}
