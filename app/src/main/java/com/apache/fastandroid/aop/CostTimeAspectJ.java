package com.apache.fastandroid.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
@Aspect
public class CostTimeAspectJ {

    /**
     *
     *
     * 一些专业术语


     Cross-cutting concerns（横切关注点）: 尽管面向对象模型中大多数类会实现单一特定的功能，
     但通常也会开放一些通用的附属功能给其他类。例如，我们希望在数据访问层中的类中添加日志，同时也希望当UI层中一个线程进入或者退出调用一个方法时添加日志。尽管每个类都有一个区别于其他类的主要功能，但在代码里，仍然经常需要添加一些相同的附属功能。

     Advice（通知）: 注入到class文件中的代码。典型的 Advice 类型有 before、after 和 around，分别表示在目标方法执行之前、执行后和完全替代目标方法执行的代码。 除了在方法中注入代码，也可能会对代码做其他修改，比如在一个class中增加字段或者接口。

     Joint point（连接点）: 程序中可能作为代码注入目标的特定的点，例如一个方法调用或者方法入口。

     Pointcut（切入点）: 告诉代码注入工具，在何处注入一段特定代码的表达式。例如，在哪些 joint points 应用一个特定的 Advice。切入点可以选择唯一一个，比如执行某一个方法，也可以有多个选择
     ，比如，标记了一个定义成@DebguTrace 的自定义注解的所有方法。

     Aspect（切面）: Pointcut 和 Advice 的组合看做切面。例如，我们在应用中通过定义一个 pointcut 和给定恰当的advice，添加一个日志切面。

     Weaving（织入）: 注入代码（advices）到目标位置（joint points）的过程。

     *
     *
     *
     * Advice：就是我们插入的代码可以以何种方式插入，有Before、 After、Around 三种类型
     *
     *
     * execution()是表达式主体
     第一个*号代表返回类型，*号代表所有的类型。
     包名 表示需要拦截的包名，这里使用*.代表匹配所有的包名。
     第二个*号表示类名，后面跟.MainActivity是指具体的类名叫MainActivity。
     *(..) 最后这个星号表示方法名，+.代表具体的函数名，*号通配符，包括括弧号里面表示方法的参数，两个dot代表任意参数。
     */




    public static final int OVER_TIME_WARN = 200;

    public static final String POINT_CUT_METHOD = "execution(@com.apache.fastandroid.annotations.CostTime * *(..))";



    @Pointcut(POINT_CUT_METHOD)
    public void executeCostTime(){

    }

    /**
     * 以 CostTime 注解的耗时监听
     * @param point
     * @throws Throwable
     */

//    @Around("executeCostTime()")
//    public void printcostTime(ProceedingJoinPoint point) throws Throwable {
//        MethodSignature methodSignature = (MethodSignature) point.getSignature();
//        long startTime = SystemClock.uptimeMillis();
//        point.proceed();
//
//        long costTime = SystemClock.uptimeMillis() - startTime;
//        if (costTime >= OVER_TIME_WARN && ThreadUtils.isMainThread()){
//            Log.d("MainLogUtil", String.format("annotation costtime %s cost %s ms", methodSignature.toString(), costTime));
//
//        }
//    }
//
//
//    /**
//     * Application 中执行方法的耗时监听
//     * @param joinPoint
//     */
//    @Around("execution(* com.apache.fastandroid.app.FastAndroidApplication.**(..))")
//    public void getApplicationCostTime(ProceedingJoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
//        long time = System.currentTimeMillis();
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        Log.d("MainLogUtil", String.format("getApplicationCostTime %s cost %s ms", name,  (System.currentTimeMillis() - time)));
//
//    }
//
//
//
//    @Around("execution(* android.app.Activity.on**(..))")
//    public void getActivityLifecycleCostTime(ProceedingJoinPoint joinPoint) {
//        Signature signature = joinPoint.getSignature();
//        String name = signature.toShortString();
//        long time = System.currentTimeMillis();
//        try {
//            joinPoint.proceed();
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        Log.d("MainLogUtil", String.format("getActivityLifecycleCostTime %s cost %s ms", name,  (System.currentTimeMillis() - time)));
//
//    }
}
