package com.tesla.framework.support.execption;

/**
 * 主流程的处理异常
 *
 */
public class HandlerException extends RuntimeException {
    public HandlerException(String detailMessage) {
        super(detailMessage);
    }
}
