package com.apache.fastandroid.network.retrofit;

import com.apache.fastandroid.network.response.BaseResponse;

/**
 * description:
 * author hui.zhu
 * data: 2020-04-24
 * copyright TCL-MIBC
 */
public interface ProtocolHandler<T> {

    void onSuccess(BaseResponse<T> protocol);

    void onFailure(int fail_code, String msg);
}