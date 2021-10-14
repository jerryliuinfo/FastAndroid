package com.coco.aspectjlib;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.ArrayList;
import java.util.List;

@Aspect
public class PermissonAspect {
    private static final String TAG = "Animal";

    @Pointcut("execution(@com.coco.aspectj.MPermisson * *(..)) && @annotation(permission)")
    public void methodAnnotatedWithMPermission(MPermisson permission) {}

    @Around("methodAnnotatedWithMPermission(permission)")
    public void checkPermission(final ProceedingJoinPoint joinPoint, MPermisson permission) throws Throwable {
        // 权限
        List<String> permissionList = new ArrayList<>();
        String permissionStr = permission.value();
        permissionList.add(permissionStr);
        Log.e(TAG,"permission="+permissionStr);
        // 模拟权限申请
//        MainActivity mainActivity = (MainActivity) joinPoint.getThis();  // 一般使用栈顶Activity作为上下文
//        ActivityCompat.requestPermissions(mainActivity,permissionList.toArray(new String[permissionList.size()]),1);
//        ActivityCompat.requestPermisson(mainActivity, Manifest.permission.CAMERA).callback(new Callback(){
//            public void onGranted(){
//                try {
//                    // 继续执行原方法
//                    joinPoint.proceed();
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            }
//            public void onDenied() {}
//        });
    }
}

//@Aspect
//public class PermissonAspect {
//    @Around("execution(@android.aspectjdemo.MPermisson * *(..)) && @annotation(permisson)")
//    public void checkPermisson(final ProceedingJoinPoint joinPoint, MPermisson permisson) throws Throwable {
//        // 权限
//        String permissonStr = permisson.value();
//        // 正常需要使用维护的栈顶Activity作为上下文，这里为了演示需要
//        MainActivity mainActivity = (MainActivity) joinPoint.getThis();          // 权限申请
//
//        Utils.requestPermisson(mainActivity, Manifest.permission.CAMERA).callback(new Callback(){
//            public void onGranted(){
//                try {
//                    // 继续执行原方法
//                    joinPoint.proceed();
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            }
//            public void onDenied() {}
//        });
//    }
//}