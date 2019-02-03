package com.apache.fastandroid.artemis.proxy;

import com.apache.fastandroid.artemis.LibraryLog;

/**
 * Created by Jerry on 2019/2/2.
 */
public class RealSubject implements ISubject {
    @Override
    public void request() {
        LibraryLog.d("RealSubject request");
    }
}
