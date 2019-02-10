package com.tesla.framework.component.intercept;

import com.tesla.framework.support.bean.InterceptorBean;

public interface InterceptorCallback {

    /**
     * Continue process
     *
     * @param interceptorBean route meta
     */
    void onContinue(InterceptorBean interceptorBean);

    /**
     * Interrupt process, pipeline will be destory when this method called.
     *
     * @param exception Reson of interrupt.
     */
    void onInterrupt(Throwable exception);
}
