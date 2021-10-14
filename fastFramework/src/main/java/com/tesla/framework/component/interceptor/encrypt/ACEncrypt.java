package com.tesla.framework.component.interceptor.encrypt;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * description:
 * author chaojiong.zhang
 * data: 2021/6/28
 * copyright TCL+
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface ACEncrypt {
    int None = -1; //不加密
    int OnlyQuery = 0; //0： 对Url query 参数加密
    int OnlyBody = 1;//1 ：对body加密(目前有application/json 和 application/x-www-form-urlencoded, 其他 body 不加密)
    int QueryAndBody = 2; // 两者都加密

    int encryptType() default QueryAndBody;

    //拓展 对query 部分字段加密 ..
}
