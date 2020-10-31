package com.github.anrwatchdog;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import android.os.Looper;

/**
 * author: jerry
 * created on: 2020/7/24 3:12 PM
 * description:
 */
public class ANRError extends Error {

    private static class ErrorStackTraceInfo implements Serializable {
        private final String _name;
        private final StackTraceElement[] _stackTrace;

        private class _Thread extends Throwable {
            private _Thread(_Thread other) {
                super(_name, other);
            }

            @Override
            public Throwable fillInStackTrace() {
                setStackTrace(_stackTrace);
                return this;
            }
        }

        private ErrorStackTraceInfo(String name, StackTraceElement[] stackTrace) {
            _name = name;
            _stackTrace = stackTrace;
        }
    }

    private static final long serialVersionUID = 1L;

    /**
     * The minimum duration, in ms, for which the main thread has been blocked. May be more.
     */
    @SuppressWarnings("WeakerAccess")
    public final long duration;

    private ANRError(ErrorStackTraceInfo._Thread st, long duration) {
        super("Application Not Responding for at least " + duration + " ms.", st);
        this.duration = duration;
    }

    @Override
    public Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[] {});
        return this;
    }

    static ANRError New(long duration, String prefix, boolean logThreadsWithoutStackTrace) {
        final Thread mainThread = Looper.getMainLooper().getThread();

        final Map<Thread, StackTraceElement[]> stackTraces = new TreeMap<Thread, StackTraceElement[]>(new Comparator<Thread>() {
            @Override
            public int compare(Thread lhs, Thread rhs) {
                if (lhs == rhs)
                    return 0;
                if (lhs == mainThread)
                    return 1;
                if (rhs == mainThread)
                    return -1;
                return rhs.getName().compareTo(lhs.getName());
            }
        });

        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet())
            if (
                    entry.getKey() == mainThread
                            ||  (
                            entry.getKey().getName().startsWith(prefix)
                                    &&  (
                                    logThreadsWithoutStackTrace
                                            ||
                                            entry.getValue().length > 0
                            )
                    )
            )
                stackTraces.put(entry.getKey(), entry.getValue());

        // Sometimes main is not returned in getAllStackTraces() - ensure that we list it
        if (!stackTraces.containsKey(mainThread)) {
            stackTraces.put(mainThread, mainThread.getStackTrace());
        }

        ErrorStackTraceInfo._Thread tst = null;
        for (Map.Entry<Thread, StackTraceElement[]> entry : stackTraces.entrySet())
            tst = new ErrorStackTraceInfo(getThreadTitle(entry.getKey()), entry.getValue()).new _Thread(tst);

        return new ANRError(tst, duration);
    }

    static ANRError NewMainOnly(long duration) {
        final Thread mainThread = Looper.getMainLooper().getThread();
        final StackTraceElement[] mainStackTrace = mainThread.getStackTrace();

        return new ANRError(new ErrorStackTraceInfo(getThreadTitle(mainThread), mainStackTrace).new _Thread(null), duration);
    }

    private static String getThreadTitle(Thread thread) {
        return thread.getName() + " (state = " + thread.getState() + ")";
    }
}