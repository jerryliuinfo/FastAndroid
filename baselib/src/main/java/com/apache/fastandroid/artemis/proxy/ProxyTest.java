package com.apache.fastandroid.artemis.proxy;

/**
 * Created by Jerry on 2019/2/2.
 */
public class ProxyTest {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();
        ProxyHandler proxyHandler = new ProxyHandler(realSubject);
        ISubject subject = (ISubject) proxyHandler.getProxyClass();
        subject.request();
    }
}
