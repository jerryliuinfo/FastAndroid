package com.apache.fastandroid.demo.kt.bean;

import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nullable;

import kotlin.io.FilesKt;

/**
 * Created by Jerry on 2022/1/23.
 */
public class JavaMain {
    public static int in = 100;


    public static  String format(String msg){
        return TextUtils.isEmpty(msg)? null:msg;
    }

    public static @Nullable String formatNullable(String msg){
        return TextUtils.isEmpty(msg)? null:msg;
    }


    public static void javaCallKotlinNotNull(){
        KotlinMain.Companion.getInstance().notAllowNull(null);
    }

    public static void javaCallKotlinNull(){
        KotlinMain.Companion.getInstance().allowNull(null);
    }

    public static void main(String[] args) {
        File file = new File(Utils.getApp().getFilesDir(), "uitest.txt");
        FilesKt.readText(file, StandardCharsets.UTF_8);
    }
}
