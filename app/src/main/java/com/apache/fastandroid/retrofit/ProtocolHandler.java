package com.apache.fastandroid.retrofit;

/**
 * description:
 * author hui.zhu
 * data: 2020-04-24
 * copyright TCL-MIBC
 */
public interface ProtocolHandler<T> {

    void onSuccess(Protocol<T> protocol);

    void onFailure(int fail_code, String msg);
}