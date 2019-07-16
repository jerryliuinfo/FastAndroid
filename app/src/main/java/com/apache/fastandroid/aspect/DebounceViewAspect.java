package com.apache.fastandroid.aspect;

import android.view.View;
import com.apache.fastandroid.util.MainLogUtil;
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
public class DebounceViewAspect {


    @Pointcut("execution(* onClick(..))")
    public void onClickPointcut() {
    }

    @Around("onClickPointcut()")
    public void aroundJoinClickPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length >= 1 && args[0] instanceof View){
            View targetView = (View) args[0];
            if (DeboucedClickPredicotor.shouldDoClick(targetView)){
                joinPoint.proceed();
            }
        }else {
            MainLogUtil.d("不是目标对象，不需要拦截");
            joinPoint.proceed();
        }

    }

}
