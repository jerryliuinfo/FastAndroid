package com.apache.fastandroid.annotations;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.apache.fastandroid.artemis.AppContext;
import com.apache.fastandroid.artemis.LoginActivity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
@Aspect
public class CheckLoginAspectJ {
    public static final String TAG = CheckLoginAspectJ.class.getSimpleName();

    /**
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

    /**
     * 第一个*表示返回值，*表示返回值为任意类型，后面这个就是典型的包名路径
     */
    @Pointcut("execution(@com.apache.fastandroid.annotations.CheckLogin * *(..))")
    public void executeCheckLogin(){

    }
    @Around("executeCheckLogin()")
    public Object checkLogin111(ProceedingJoinPoint point) throws Throwable {
        Log.d(TAG, "checkLogin Around----->"+point.getSignature().toString() +", target = "+point.getTarget() +", args = "+ point.getArgs());
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        CheckLogin checkLogin = methodSignature.getMethod().getAnnotation(CheckLogin.class);
        if (checkLogin != null){
            if (AppContext.isLogined()){
                Log.d(TAG, "已登录,直接跳转");
                return point.proceed();
            }else {
                Object object = point.getThis();
                Log.d(TAG, "未登录,先跳转登录页面 object = "+ object);
                Context context = (Context) point.getThis();
                context.startActivity(new Intent(context,LoginActivity.class));

                return null;
            }
        }else {
            return point.proceed();
        }
    }







    @Pointcut("execution(@com.apache.fastandroid.annotations.DebugTrace * *(..))")
    public void debugTraceMethod(){

    }

    @Before("debugTraceMethod()")
    public void beforeDebugTrace(JoinPoint point){
        String key = point.getSignature().toString();
        Log.d(TAG, "beforeDebugTrace:"+key);
    }



}
