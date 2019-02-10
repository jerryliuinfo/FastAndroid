package com.tesla.framework.component.intercept;

/**
 * Used for inject custom logic when navigation.
 *
 * @author Alex <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 16/8/23 13:56
 */
public interface IInterceptor {

    /**
     * The operation of this interceptor.
     *
     * @param callback cb
     */
    void process(InterceptorCallback callback);
}
