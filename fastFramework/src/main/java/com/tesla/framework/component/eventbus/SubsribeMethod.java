package com.tesla.framework.component.eventbus;

import java.lang.reflect.Method;

/**
 * author: 01370340
 * data: 2019-10-17
 * description:
 */
public class SubsribeMethod {
    Method method;
    Subscribe subscribe;
    Class<?> type;

    public SubsribeMethod(Method method, Subscribe subscribe, Class<?> type) {
        this.method = method;
        this.subscribe = subscribe;
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SubsribeMethod{");
        sb.append("method=").append(method);
        sb.append(", subscribe=").append(subscribe);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Subscribe getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Subscribe subscribe) {
        this.subscribe = subscribe;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
