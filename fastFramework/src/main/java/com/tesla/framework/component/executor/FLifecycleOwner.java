package com.tesla.framework.component.executor;

import androidx.lifecycle.Lifecycle;
import androidx.annotation.NonNull;

/**
 * Created by Jerry on 2020-05-16.
 */
public interface FLifecycleOwner {
    /**
     * Returns the FLifecycle of the provider.
     *
     * @return The lifecycle of the provider.
     */
    @NonNull
    Lifecycle getLifecycle();
}
