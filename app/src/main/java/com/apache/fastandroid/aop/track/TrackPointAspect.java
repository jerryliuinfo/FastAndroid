package com.apache.fastandroid.aop.track;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
@Aspect
public class TrackPointAspect {

    @Pointcut("execution(* onClick(..))")
    public void onClickPointcut() {
    }

    @Pointcut("execution(* android.app.Activity+.onCreate(..))")
    public void activityOnCreatePointcut() {
    }

    @Pointcut("execution(* android.app.Activity+.onDestroy(..))")
    public void activityOnDestroyPointcut() {
    }

    @Pointcut("execution(* android.app.Fragment+.onCreate(..))")
    public void fragmentOnCreatePointcut() {
    }

    @Pointcut("execution(* android.support.v4.app.Fragment+.onCreate(..))")
    public void fragmentV4OnCreatePointcut() {
    }

    @Pointcut("execution(* android.app.Fragment+.onDestroy(..))")
    public void fragmentOnDestroyPointcut() {
    }

    @Pointcut("execution(* android.support.v4.app.Fragment+.onDestroy(..))")
    public void fragmentV4OnDestroyPointcut() {
    }


}
