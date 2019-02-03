package com.apache.fastandroid.artemis.proxy;

import com.apache.fastandroid.artemis.LibraryLog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Jerry on 2019/2/2.
 */
public class ProxyHandler implements InvocationHandler {
    private ISubject subject;

    public ProxyHandler(ISubject subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LibraryLog.d("before invoke");
        Object result = method.invoke(subject,args);
        LibraryLog.d("after invoke");
        return result;
    }


    public Object getProxyClass(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),subject.getClass().getInterfaces(),this);
    }
}
