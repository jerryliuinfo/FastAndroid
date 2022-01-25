package com.apache.fastandroid.demo.kt.bean;

import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.nio.charset.StandardCharsets;

import kotlin.io.FilesKt;

/**
 * Created by Jerry on 2022/1/23.
 */
public class JavaMain {
    public static int in = 100;


    public static String format(String msg){
        return TextUtils.isEmpty(msg)? null:msg;
    }

    public static void main(String[] args) {
        File file = new File(Utils.getApp().getFilesDir(), "test.txt");
        FilesKt.readText(file, StandardCharsets.UTF_8);
    }
}
