package com.coco.aspectjlib;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ConstructorAspect {
    private static final String TAG = "Animal";

    @Before("execution(com.coco.aspectj.Animal.new(..))")
    public void beforeConstructorExecution(JoinPoint joinPoint) {
        Log.e(TAG, "ConstructorAspect before->" + joinPoint.getThis().toString() + "#" + joinPoint.getSignature().getName());
    }
}