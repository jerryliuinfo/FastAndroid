package com.balsikandar.crashreporter.utils;


public class CrashReporterNotInitializedException extends CrashReporterException {
    static final long serialVersionUID = 1;


    public CrashReporterNotInitializedException() {
        super();
    }


    public CrashReporterNotInitializedException(String message) {
        super(message);
    }


    public CrashReporterNotInitializedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CrashReporterNotInitializedException(Throwable throwable) {
        super(throwable);
    }
}