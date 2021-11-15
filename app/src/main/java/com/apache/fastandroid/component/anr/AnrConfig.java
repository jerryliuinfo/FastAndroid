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

    public static AnrConfig.Builder with(){
        return new AnrConfig.Builder();
    }

    public AnrConfig(Builder builder) {
        this._timeoutInterval = builder._timeoutInterval;
        this._namePrefix = builder._namePrefix;
        this._logThreadsWithoutStackTrace = builder._logThreadsWithoutStackTrace;
        this._ignoreDebugger = builder._ignoreDebugger;
        this.anrListener = builder.anrListener;
        this.anrInterceptor = builder.anrInterceptor;
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

    public Builder newBuilder(){
        return new Builder(this);
    }

    public static class Builder{
        private  int _timeoutInterval;

        private String _namePrefix = "";
        private boolean _logThreadsWithoutStackTrace = false;
        private boolean _ignoreDebugger = false;

        private ANRWatchDog.ANRListener anrListener;
        private ANRWatchDog.ANRInterceptor anrInterceptor;

        public Builder(AnrConfig config){
            this._timeoutInterval = config._timeoutInterval;
            this._namePrefix = config._namePrefix;
            this._logThreadsWithoutStackTrace = config._logThreadsWithoutStackTrace;
            this._ignoreDebugger = config._ignoreDebugger;
            this.anrListener = config.anrListener;
            this.anrInterceptor = config.anrInterceptor;
        }

        public Builder() {
        }

        public Builder set_timeoutInterval(int _timeoutInterval) {
            this._timeoutInterval = _timeoutInterval;
            return this;
        }

        public Builder set_namePrefix(String _namePrefix) {
            this._namePrefix = _namePrefix;
            return this;
        }

        public Builder set_logThreadsWithoutStackTrace(boolean _logThreadsWithoutStackTrace) {
            this._logThreadsWithoutStackTrace = _logThreadsWithoutStackTrace;
            return this;
        }

        public Builder setIgnoreDebugger(boolean _ignoreDebugger) {
            this._ignoreDebugger = _ignoreDebugger;
            return this;
        }

        public Builder setAnrListener(ANRWatchDog.ANRListener anrListener) {
            this.anrListener = anrListener;
            return this;
        }

        public Builder setAnrInterceptor(ANRWatchDog.ANRInterceptor anrInterceptor) {
            this.anrInterceptor = anrInterceptor;
            return this;
        }

        public Builder setReportMainThreadOnly(){
            this._namePrefix = "";
            return this;
        }

        public AnrConfig build(){
            return new AnrConfig(this);
        }
    }
}
