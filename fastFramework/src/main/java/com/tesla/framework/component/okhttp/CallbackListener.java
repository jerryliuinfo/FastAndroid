package com.tesla.framework.component.okhttp;

import java.io.InputStream;

/**
 * Created by Jerry on 2019-12-29.
 */
public interface CallbackListener {

    void onSuccess(InputStream inputStream);

    void onFail(Throwable throwable);
}
