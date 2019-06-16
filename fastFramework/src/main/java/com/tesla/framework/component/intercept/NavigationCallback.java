package com.tesla.framework.component.intercept;

/**
 * ActivityForResultCallback after navigation.
 *
 * @author Alex <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 2016/9/22 14:15
 */
public interface NavigationCallback {

    /**
     * ActivityForResultCallback when find the destination.
     *
     */
    void onFound();

    /**
     * ActivityForResultCallback after lose your way.
     *
     */
    void onLost();

    /**
     * ActivityForResultCallback after navigation.
     *
     */
    void onArrival();

    /**
     * ActivityForResultCallback on interrupt.
     *
     */
    void onInterrupt();
}
