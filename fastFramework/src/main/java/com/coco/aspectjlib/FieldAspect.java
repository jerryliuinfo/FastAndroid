package com.coco.aspectjlib;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class FieldAspect {
    private static final String TAG = "Animal";

    @Around("get(int com.coco.aspectj.Animal.age)")
    public int aroundFieldGet(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行原代码
        Object obj = joinPoint.proceed();
        int age = Integer.parseInt(obj.toString());
        Log.e(TAG, "FieldAspect age: " + age);
        return 100;
    }

    /**
     * withincode 解决构造函数也被替换
     * @param joinPoint
     * @throws Throwable
     */
    @Around("set(int com.coco.aspectj.Animal.age) && !withincode(com.coco.aspectj..*.new(..))")
    public void aroundFieldSet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "FieldAspect around->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
    }
}