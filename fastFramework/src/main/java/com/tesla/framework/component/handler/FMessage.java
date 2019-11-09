package com.tesla.framework.component.handler;

/**
 * author: 01370340
 * data: 2019-10-18
 * description:
 */
public class FMessage {
    public int what;

    public Object obj;

    public FHandler target;

    public FMessage() {

    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FMessage{");
        sb.append("what=").append(what);
        sb.append(", obj=").append(obj);
        sb.append('}');
        return sb.toString();
    }
}
