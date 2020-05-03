package com.apache.artemis_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jerry on 2020-04-28.
 */

@Target(ElementType.TYPE) //作用域放到类上面
////声明注解的生命周期为编译期,安装到手机其实是没有这个注解的
@Retention(RetentionPolicy.SOURCE)
public @interface BindPath {
    String value() default "";

}


