package com.tesla.framework.ui.widget;

import android.view.View;
import java.util.concurrent.TimeUnit;

/**
 * Created by 01370340 on 2017/12/23.
 */

public abstract class DoubleClickListener implements View.OnClickListener {
    private final long debounceIntervalInMillis;
    private long previousClickTimestamp;

    public DoubleClickListener(long debounceIntervalInMillis) {
        this.debounceIntervalInMillis = debounceIntervalInMillis;
    }

    @Override
    public void onClick(View view) {
        final long currentClickTimestamp = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());

        if (previousClickTimestamp == 0 || currentClickTimestamp - previousClickTimestamp >= debounceIntervalInMillis) {
            //update click timestamp
            previousClickTimestamp = currentClickTimestamp;

            onDebouncedClick(view);
        }
    }

    protected abstract void onDebouncedClick(View v);
}
