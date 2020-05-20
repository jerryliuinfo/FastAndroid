package com.tesla.framework.component.executor;

import android.support.annotation.Nullable;

/**
 * Created by Jerry on 2020-05-16.
 */
public interface Observer<T> {
    /**
     * Called when the data is changed.
     * @param t  The new data
     */
    void onChanged(@Nullable T t);
}
