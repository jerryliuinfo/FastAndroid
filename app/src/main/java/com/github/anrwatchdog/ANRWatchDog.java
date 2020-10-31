package com.github.anrwatchdog;

import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * author: jerry
 * created on: 2020/7/24 3:12 PM
 * description:
 */
public class ANRWatchDog extends Thread {
    private static final int DEFAULT_ANR_TIMEOUT = 5000;
    private static final ANRWatchDog.ANRListener DEFAULT_ANR_LISTENER = new ANRWatchDog.ANRListener() {
        public void onAppNotResponding(ANRError error) {
            throw error;
        }
    };
    private static final ANRWatchDog.ANRInterceptor DEFAULT_ANR_INTERCEPTOR = new ANRWatchDog.ANRInterceptor() {
        public long intercept(long duration) {
            return 0L;
        }
    };
    private static final ANRWatchDog.InterruptionListener DEFAULT_INTERRUPTION_LISTENER = new ANRWatchDog.InterruptionListener() {
        public void onInterrupted(InterruptedException exception) {
            Log.w("ANRWatchdog", "Interrupted: " + exception.getMessage());
        }
    };
    private ANRWatchDog.ANRListener _anrListener;
    private ANRWatchDog.ANRInterceptor _anrInterceptor;
    private ANRWatchDog.InterruptionListener _interruptionListener;
    private final Handler _uiHandler;
    private final int _timeoutInterval;
    private String _namePrefix;
    private boolean _logThreadsWithoutStackTrace;
    private boolean _ignoreDebugger;
    private volatile long _tick;
    private volatile boolean _reported;
    private final Runnable _ticker;

    public ANRWatchDog() {
        this(5000);
    }

    public ANRWatchDog(int timeoutInterval) {
        this._anrListener = DEFAULT_ANR_LISTENER;
        this._anrInterceptor = DEFAULT_ANR_INTERCEPTOR;
        this._interruptionListener = DEFAULT_INTERRUPTION_LISTENER;
        this._uiHandler = new Handler(Looper.getMainLooper());
        this._namePrefix = "";
        this._logThreadsWithoutStackTrace = false;
        this._ignoreDebugger = false;
        this._tick = 0L;
        this._reported = false;
        this._ticker = new Runnable() {
            public void run() {
                ANRWatchDog.this._tick = 0L;
                ANRWatchDog.this._reported = false;
            }
        };
        this._timeoutInterval = timeoutInterval;
    }

    public int getTimeoutInterval() {
        return this._timeoutInterval;
    }

    public ANRWatchDog setANRListener(ANRWatchDog.ANRListener listener) {
        if (listener == null) {
            this._anrListener = DEFAULT_ANR_LISTENER;
        } else {
            this._anrListener = listener;
        }

        return this;
    }

    public ANRWatchDog setANRInterceptor(ANRWatchDog.ANRInterceptor interceptor) {
        if (interceptor == null) {
            this._anrInterceptor = DEFAULT_ANR_INTERCEPTOR;
        } else {
            this._anrInterceptor = interceptor;
        }

        return this;
    }

    public ANRWatchDog setInterruptionListener(ANRWatchDog.InterruptionListener listener) {
        if (listener == null) {
            this._interruptionListener = DEFAULT_INTERRUPTION_LISTENER;
        } else {
            this._interruptionListener = listener;
        }

        return this;
    }

    public ANRWatchDog setReportThreadNamePrefix(String prefix) {
        if (prefix == null) {
            prefix = "";
        }

        this._namePrefix = prefix;
        return this;
    }

    public ANRWatchDog setReportMainThreadOnly() {
        this._namePrefix = null;
        return this;
    }

    public ANRWatchDog setReportAllThreads() {
        this._namePrefix = "";
        return this;
    }

    public ANRWatchDog setLogThreadsWithoutStackTrace(boolean logThreadsWithoutStackTrace) {
        this._logThreadsWithoutStackTrace = logThreadsWithoutStackTrace;
        return this;
    }

    public ANRWatchDog setIgnoreDebugger(boolean ignoreDebugger) {
        this._ignoreDebugger = ignoreDebugger;
        return this;
    }

    public void run() {
        this.setName("|ANR-WatchDog|");
        long interval = (long)this._timeoutInterval;

        while(true) {
            while(true) {
                do {
                    do {
                        if (this.isInterrupted()) {
                            return;
                        }

                        boolean needPost = this._tick == 0L;
                        this._tick += interval;
                        if (needPost) {
                            this._uiHandler.post(this._ticker);
                        }

                        try {
                            Thread.sleep(interval);
                        } catch (InterruptedException var5) {
                            this._interruptionListener.onInterrupted(var5);
                            return;
                        }
                    } while(this._tick == 0L);
                } while(this._reported);

                if (!this._ignoreDebugger && (Debug.isDebuggerConnected() || Debug.waitingForDebugger())) {
                    Log.w("ANRWatchdog", "An ANR was detected but ignored because the debugger is connected (you can prevent this with setIgnoreDebugger(true))");
                    this._reported = true;
                } else {
                    interval = this._anrInterceptor.intercept(this._tick);
                    if (interval <= 0L) {
                        ANRError error;
                        if (this._namePrefix != null) {
                            error = ANRError.New(this._tick, this._namePrefix, this._logThreadsWithoutStackTrace);
                        } else {
                            error = ANRError.NewMainOnly(this._tick);
                        }

                        this._anrListener.onAppNotResponding(error);
                        interval = this._timeoutInterval;
                        this._reported = true;
                    }
                }
            }
        }
    }

    public interface InterruptionListener {
        void onInterrupted(InterruptedException var1);
    }

    public interface ANRInterceptor {
        long intercept(long var1);
    }

    public interface ANRListener {
        void onAppNotResponding(ANRError var1);
    }
}
