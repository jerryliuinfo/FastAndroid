package com.apache.fastandroid.demo.designmode.proxy.dynamic;

import com.tesla.framework.common.util.log.FastLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Jerry on 2019/2/2.
 */
public class ProxyHandler implements InvocationHandler {
    public static final String TAG = "ProxyHandler";
    private ISubject subject;

    public ProxyHandler(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        FastLog.d(TAG,"before invoke");
        Object result = method.invoke(subject,args);
        FastLog.d(TAG, "after invoke");
        return result;
    }


    public Object getProxyClass(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),subject.getClass().getInterfaces(),this);
    }
}
