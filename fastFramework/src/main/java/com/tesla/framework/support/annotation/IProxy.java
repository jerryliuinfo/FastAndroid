package com.tesla.framework.support.annotation;

import android.view.View;

/**
 * Created by Jerry on 2019/2/4.
 */
public interface IProxy<T> {
    /**
     *
     * @param target 所在的类
     * @param root 查找 View 的地方
     */
    void inject(T target, View root);
}
