package com.tesla.framework.component.crashreporter.utils;


public class CrashReporterException extends RuntimeException {
    static final long serialVersionUID = 1;


    public CrashReporterException() {
        super();
    }


    public CrashReporterException(String message) {
        super(message);
    }


    public CrashReporterException(String format, Object... args) {
        this(String.format(format, args));
    }


    public CrashReporterException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CrashReporterException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String toString() {
        // Throwable.toString() returns "CrashReporterException:{message}". Returning just "{message}"
        // should be fine here.
        return getMessage();
    }
}
