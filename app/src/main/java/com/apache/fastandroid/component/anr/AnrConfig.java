package com.apache.fastandroid.component.anr;

import com.github.anrwatchdog.ANRWatchDog;

/**
 * Created by Jerry on 2021/9/24.
 */
public class AnrConfig {
    private  int _timeoutInterval;

    private String _namePrefix = "";
    private boolean _logThreadsWithoutStackTrace = false;
    private boolean _ignoreDebugger = false;

    private ANRWatchDog.ANRListener anrListener;
    private ANRWatchDog.ANRInterceptor anrInterceptor;

    public static AnrConfig with(){
        return new AnrConfig();
    }


    public AnrConfig set_timeoutInterval(int _timeoutInterval) {
        this._timeoutInterval = _timeoutInterval;
        return this;
    }

    public AnrConfig set_namePrefix(String _namePrefix) {
        this._namePrefix = _namePrefix;
        return this;
    }

    public AnrConfig set_logThreadsWithoutStackTrace(boolean _logThreadsWithoutStackTrace) {
        this._logThreadsWithoutStackTrace = _logThreadsWithoutStackTrace;
        return this;
    }

    public AnrConfig set_ignoreDebugger(boolean _ignoreDebugger) {
        this._ignoreDebugger = _ignoreDebugger;
        return this;
    }


    public int get_timeoutInterval() {
        return _timeoutInterval;
    }

    public String get_namePrefix() {
        return _namePrefix;
    }

    public boolean is_logThreadsWithoutStackTrace() {
        return _logThreadsWithoutStackTrace;
    }

    public boolean is_ignoreDebugger() {
        return _ignoreDebugger;
    }

    public ANRWatchDog.ANRListener getAnrListener() {
        return anrListener;
    }

    public AnrConfig setAnrListener(ANRWatchDog.ANRListener anrListener) {
        this.anrListener = anrListener;
        return this;
    }

    public AnrConfig setReportThreadNamePrefix(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        _namePrefix = prefix;
        return this;
    }

    public AnrConfig setReportMainThreadOnly() {
        _namePrefix = null;
        return this;
    }

    public ANRWatchDog.ANRInterceptor getAnrInterceptor() {
        return anrInterceptor;
    }

    public AnrConfig setAnrInterceptor(ANRWatchDog.ANRInterceptor anrInterceptor) {
        this.anrInterceptor = anrInterceptor;
        return this;
    }
}
