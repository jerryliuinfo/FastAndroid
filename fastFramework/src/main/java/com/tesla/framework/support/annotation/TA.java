package com.tesla.framework.support.annotation;

/**
 * @auther tb
 * @time 2018/3/27 下午2:40
 * @desc
 */
public interface TA<T> {
    void inject(T t, Object o);
}
