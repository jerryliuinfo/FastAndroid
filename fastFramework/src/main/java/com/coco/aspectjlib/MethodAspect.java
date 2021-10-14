package com.coco.aspectjlib;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class MethodAspect {
    private static final String TAG = "Animal";

    // 所有activity的onCreate方法
    private static final String Activity_create_POINTCUTS = "execution(* androidx.appcompat.app.AppCompatActivity.onCreate(..))";

    // 使用 @Pointcut 来注解方法,call(MethodSignature) 关键字表示方法被调用
    @Pointcut(Activity_create_POINTCUTS)
    public void onActivityCreatePointcuts() {
    }

    @Before("onActivityCreatePointcuts()")
    public void beforeCreate(JoinPoint joinPoint) throws Throwable {
        Log.e("打开新页面", joinPoint.getThis() + "  onCreate");
        Util.saveInfo("打开新页面", joinPoint.getThis() + "  onCreate");
    }

    /**
     * 所有的点击事件，收集点击view所在的类，view的id，执行时间
     * @param joinPoint
     * @throws Throwable
     */
    @Around("execution(* *(android.view.View))")
    public void aroundAllMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        View view = Util.getViewFromArgs(args);
        if (view == null) {
            Log.d(TAG, "unknown type method, so proceed it");
            joinPoint.proceed();
            return;
        }
        //获取View 的 string id
        String resEntryName = null;
        String resName = null;
        resEntryName = view.getContext().getResources().getResourceEntryName(view.getId());
        resName = view.getContext().getResources().getResourceName(view.getId());

        Util.saveInfo("click", "点前所在类：" + view.getContext() + "  点击view id：" + resEntryName);
        Log.e("click", "点前所在类：" + view.getContext() + "  " + resEntryName + "  view id：" + resName);
        // 执行原代码
        joinPoint.proceed();
    }


    /**
     * 某个类里面的所有方法调用
     * 不能和Before、After一起使用,否则没有任何效果
     *
     * @param joinPoint
     * @throws Throwable
     */
    private static final String CLASS_ONE_POINTCUTS = "execution(* com.apache.fastandroid.MainActivity.*(..))";
    private static final String CLASS_TEST_POINTCUTS = "execution(* com.coco.aspectj.TestActivity.*(..))";

    @Pointcut(CLASS_ONE_POINTCUTS)
    public void onMainActivityPointcuts() {
    }

    @Pointcut(CLASS_TEST_POINTCUTS)
    public void onTestActivityPointcuts() {
    }

    @Before("onMainActivityPointcuts() || onTestActivityPointcuts()")
    public void aroundMethodCall1(JoinPoint joinPoint) throws Throwable {
        Log.e("function", "当前类名：" + joinPoint.getTarget().toString() + "  方法名：" + joinPoint.getSignature().getName());
        Util.saveInfo("function", "当前类名：" + joinPoint.getTarget().toString() + "  方法名：" + joinPoint.getSignature().getName());

    }

    /**
     * 捕获应该ANR等info
     * 需要注意：如果目标方法中出现异常，并由catch捕捉处理且catch又没有抛出新的异常，那么针对该目标方法的AfterThrowing增强处理将不会被执行。
     * 异常捕获
     * @param ex
     */
    @AfterThrowing(pointcut = "execution(* *..*(..))", throwing = "ex")
    public void anyFuncThrows(Throwable ex) {
        StackTraceElement[] stackElements = ex.getStackTrace();//通过Throwable获得堆栈信息
        String temp = ex.getMessage() + "\r\n";
        if (stackElements != null) {
            for (int i = 0; i < stackElements.length; i++) {
                temp += "        " + stackElements[i].toString() + "\r\n";
            }
            Util.saveInfo("error", temp);
        }
    }

    /**
     * handler 用来匹配 catch 的异常，
     * 不支持@After、@Around
     */
//    @Before("handler(java.lang.ArithmeticException)")
//    public void handler(Throwable throwable) {
//        Log.e("handler", "**************hurtThrows: ", throwable);
//        Util.saveInfo("handler", throwable+"");
//    }


    // 接口调用


    @After("execution(* *.onResume(..))")
    public void onResumeMethod(JoinPoint joinPoint) throws Throwable {
        Log.e("coco", "resume=" + joinPoint.getThis());
        Util.saveInfo(TAG, joinPoint.getThis() + "  onResume");

    }

    @After("execution(* androidx.fragment.app.FragmentActivity.onPause(..))")
    public void onPauseMethod(JoinPoint joinPoint) throws Throwable {
        Log.e("coco", "pause=" + joinPoint.getThis());
    }


    /**
     * 不能和Before、After一起使用,否则没有任何效果
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("call(* com.coco.aspectj.Animal.fly(..))")
    public void aroundMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "MethodAspect around->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
        Util.saveInfo(TAG, "MethodAspect around->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
        // 执行原代码
        joinPoint.proceed();
    }

    /**
     * execution 关键字表示方法执行内部
     *
     * @param joinPoint
     */
    @Before("execution(* com.coco.aspectj.Animal.fly(..))")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        Log.e(TAG, "MethodAspect before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
        Util.saveInfo(TAG, "MethodAspect before->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
    }
    private static final String one_POINTCUTS = "execution(* *.getHeight(..))";
    private static final String two_POINTCUTS = "execution(* *.getTest(..))";

    // 使用 @Pointcut 来注解方法,call(MethodSignature) 关键字表示方法被调用
    @Pointcut(one_POINTCUTS)
    public void onePointcuts() {
    }
    @Pointcut(two_POINTCUTS)
    public void twoPointcuts() {
    }
    @AfterReturning(pointcut = "onePointcuts() || twoPointcuts()", returning = "height")
//    @AfterReturning(pointcut = "execution(* *.getHeight())", returning = "height")
    public void getHeight11(JoinPoint joinPoint,Object height) {
        Log.d("coco","h="+height);
        Log.e("coco", "MethodAspect around->" + joinPoint.getTarget().toString() + "#" + joinPoint.getSignature().getName());
        if(joinPoint.getTarget() == null){
            return;
        }
        Object[] args = joinPoint.getArgs();
        String argsStr ="";
        if(args!=null && args.length>0){
            argsStr ="\r\n参数：";
            for(Object obj:args){
                if(obj!=null){
                    argsStr += obj.toString() + "\r\n";
                }
            }
        }
        Log.e("coco","argsStr="+argsStr);
        Log.e("coco", "当前类名：" + joinPoint.getTarget().toString() + "  方法名：" + joinPoint.getSignature().getName());
        Util.saveInfo(TAG, "当前类名：" + joinPoint.getTarget().toString() + "  方法名：" + joinPoint.getSignature().getName()
                + argsStr);
    }

//    @Before("onePointcuts() || twoPointcuts()")
//    public void beforeCreate22(JoinPoint joinPoint) throws Throwable {
//        if(joinPoint.getTarget() == null){
//            return;
//        }
//        Object[] args = joinPoint.getArgs();
//        String argsStr ="";
//        if(args!=null && args.length>0){
//            argsStr ="\r\n参数：";
//            for(Object obj:args){
//                if(obj!=null){
//                    argsStr += obj.toString() + "\r\n";
//                }
//            }
//        }
//        Log.e("coco","argsStr befor ="+argsStr);
//        Util.saveInfo("打开新页面", joinPoint.getThis() + "  onCreate");
//    }
}