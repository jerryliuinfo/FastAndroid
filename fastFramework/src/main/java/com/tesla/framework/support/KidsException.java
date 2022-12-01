package com.tesla.framework.support;

/**
 * Created by Jerry on 2021/3/24.
 */
public class KidsException extends RuntimeException {
    public static final int CODE_INIT = -9998;
    public int code = CODE_INIT;
    public Object data;
    public String message;



    private KidsException(Throwable cause) {
        super(cause);
    }

    public static KidsException newException(int code, String message, Object... data){
        KidsException exception =  new KidsException(code,message);
        exception.data = data;
        return exception;
    }


    public static KidsException newException(String msg){
        return new KidsException(CODE_INIT, msg);
    }


    public static final int EMPTY_DATA = 888;
    public static KidsException emptyDataException(){
        return new KidsException(EMPTY_DATA, "empty data");
    }

    public KidsException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }



    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":").append(code);
        sb.append("\"message\":").append(getMessage());
        sb.append(",\"data\":\"").append(data).append('\"');
        sb.append('}');
        return sb.toString();
    }


}
