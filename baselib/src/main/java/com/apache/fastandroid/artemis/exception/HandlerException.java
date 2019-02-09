package com.apache.fastandroid.artemis.exception;

/**
 * 主流程的处理异常
 *
 */
public class HandlerException extends RuntimeException {
    public HandlerException(String detailMessage) {
        super(detailMessage);
    }
}
