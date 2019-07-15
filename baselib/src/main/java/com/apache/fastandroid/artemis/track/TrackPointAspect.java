package com.apache.fastandroid.artemis.track;

import android.view.View;
import com.apache.fastandroid.artemis.util.BaseLibLogUtil;
import com.tesla.framework.common.util.N;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

    @Around("onClickPointcut()")
    public void aroundJoinClickPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        String className = getTargetClassaName(joinPoint);
        //获取点击事件view对象及名称，可以对不同按钮的点击事件进行统计
        Object[] args = joinPoint.getArgs();
        BaseLibLogUtil.d("aroundJoinClickPoint --->target = %s, className = %s, args = %s, args[0] is view = %s",joinPoint.getTarget(),className,args,args[0] instanceof View );

        if (args.length >= 1 && args[0] instanceof View && !N.isEmpty(className)) {
            View view = (View) args[0];
            int id = view.getId();
            try {
                String entryName = view.getResources().getResourceEntryName(id);
                TrackPoint.onClick(className, entryName);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        joinPoint.proceed();//执行原来的代码
    }

    @Around("activityOnCreatePointcut() || fragmentOnCreatePointcut() || fragmentV4OnCreatePointcut()")
    public void aroundJoinPageOpenPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        String className = getTargetClassaName(joinPoint);
        TrackPoint.onPageOpen(className);
        joinPoint.proceed();
    }

    @Around("activityOnDestroyPointcut() || fragmentOnDestroyPointcut() || fragmentV4OnDestroyPointcut()")
    public void aroundJoinPageClosePoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        String className = getTargetClassaName(joinPoint);
        TrackPoint.onPageClose(className);
        joinPoint.proceed();
    }

    private String getTargetClassaName(ProceedingJoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        String className = "";
        if (target != null) {
            className = target.getClass().getName();
        }
        return className;
    }
}
