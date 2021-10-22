package com.apache.fastandroid.demo.designmode.proxy.dynamic;

import com.tesla.framework.common.util.log.FastLog;

/**
 * Created by Jerry on 2019/2/2.
 */
public class RealSubject implements ISubject {
    @Override
    public void request() {
        FastLog.d("","RealSubject request");
    }
}
