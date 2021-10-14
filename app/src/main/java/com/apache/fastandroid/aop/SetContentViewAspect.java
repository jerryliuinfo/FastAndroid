package com.apache.fastandroid.aop;

/**
 * Created by Jerry on 2020-05-13.
 */
//@Aspect
//public class SetContentViewAspect {
//
//    @Around("execution(* android.app.Activity.setContentView(..))")
//    public void getSetContentViewTime(ProceedingJoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
//        long time = System.currentTimeMillis();
//        try {
//            joinPoint.proceed();
//            System.out.println(System.currentTimeMillis() - time);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//}
