package com.coco.aspectjlib;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * 如果要在static块初始化之前，插入代码：
 */
@Aspect
public class StaticInitializationAspect {
    private static final String TAG = "Animal";

    @Before("staticinitialization(com.coco.aspectj.Animal)")
    public void beforeStaticBlock(JoinPoint joinPoint) {
        Log.d(TAG, "StaticInitializationAspect beforeStaticBlock: ");
    }
}