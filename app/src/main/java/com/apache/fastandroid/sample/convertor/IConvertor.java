package com.apache.fastandroid.sample.convertor;

import com.tesla.framework.support.ICommonCallback;

/**
 * Created by  on 2021/12/18.
 */
public interface IConvertor<T> {

   Class<? extends ICommonCallback> map(T t);
}
