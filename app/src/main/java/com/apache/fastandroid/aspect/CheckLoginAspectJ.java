package com.apache.fastandroid.aspect;

import android.content.Context;
import android.content.Intent;
import com.apache.fastandroid.annotations.CheckLogin;
import com.apache.fastandroid.artemis.AppContext;
import com.apache.fastandroid.artemis.LoginActivity;
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
public class CheckLoginAspectJ {

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
        MainLogUtil.d("checkLogin Around----->"+point.getSignature().toString() +", target = "+point.getTarget() +", args = "+ point.getArgs());
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        CheckLogin checkLogin = methodSignature.getMethod().getAnnotation(CheckLogin.class);
        if (checkLogin != null){
            if (AppContext.isLogined()){
                MainLogUtil.d("已登录,直接跳转 value = %s", checkLogin.value());
                Object result = point.proceed();
                MainLogUtil.d("登录后干点啥事.....");
                return result;
            }else {
                Object object = point.getThis();
                MainLogUtil.d("未登录,先跳转登录页面 object = "+ object);
                Context context = (Context) point.getThis();
                context.startActivity(new Intent(context,LoginActivity.class));

                return null;
            }
        }else {
            MainLogUtil.d("未添加CheckLogin注解，啥也不用干");
            Object result = point.proceed();
            return result;
        }
    }
}
