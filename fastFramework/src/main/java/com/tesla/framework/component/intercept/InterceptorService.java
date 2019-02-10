package com.tesla.framework.component.intercept;


import com.tesla.framework.support.bean.InterceptorBean;

/**
 * Interceptor service
 *
 * @author zhilong <a href="mailto:zhilong.lzl@alibaba-inc.com">Contact me.</a>
 * @version 1.0
 * @since 2017/2/23 下午2:06
 */
public interface InterceptorService extends IProvider {

    /**
     * Do interceptions
     */
    void doInterceptions(InterceptorBean bean,InterceptorCallback callback);
}
