package com.apache.fastandroid.aspect;

import android.os.SystemClock;
import com.apache.fastandroid.util.MainLogUtil;
import com.tesla.framework.support.thread.ThreadUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
@Aspect
public class CostTimeAspectJ {

    public static final int OVER_TIME_WARN = 200;

    @Pointcut("execution(@com.apache.fastandroid.annotations.CostTime * *(..))")
    public void executeCostTime(){

    }

    @Around("executeCostTime()")
    public void printcostTime(ProceedingJoinPoint point) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        MainLogUtil.d("printcostTime point = %s",methodSignature.toString());
        long startTime = SystemClock.uptimeMillis();

        point.proceed();

        long costTime = SystemClock.uptimeMillis() - startTime;
        MainLogUtil.d("printcostTime costTime = %s ms, boolean isMainThread = %s", costTime, ThreadUtils.isMainThread());
        if (costTime >= OVER_TIME_WARN){
            MainLogUtil.e("方法 %s run in main thread --->, cost time = %s ms, over %s ms",methodSignature.toString(), costTime,OVER_TIME_WARN);
        }
    }
}
