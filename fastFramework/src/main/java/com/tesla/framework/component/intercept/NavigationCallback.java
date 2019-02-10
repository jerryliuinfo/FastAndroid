package com.tesla.framework.component.intercept;

/**
 * Callback after navigation.
 *
 * @author Alex <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 2016/9/22 14:15
 */
public interface NavigationCallback {

    /**
     * Callback when find the destination.
     *
     */
    void onFound();

    /**
     * Callback after lose your way.
     *
     */
    void onLost();

    /**
     * Callback after navigation.
     *
     */
    void onArrival();

    /**
     * Callback on interrupt.
     *
     */
    void onInterrupt();
}
