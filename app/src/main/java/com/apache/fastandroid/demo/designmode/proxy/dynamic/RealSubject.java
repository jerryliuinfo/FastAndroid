package com.apache.fastandroid.demo.designmode.proxy.dynamic;

import com.tesla.framework.component.logger.Logger;

/**
 * Created by Jerry on 2019/2/2.
 */
public class RealSubject implements ISubject {
    @Override
    public void request() {
        Logger.d( "RealSubject request");
    }
}
