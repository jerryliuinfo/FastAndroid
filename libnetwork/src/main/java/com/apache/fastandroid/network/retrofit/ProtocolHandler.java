package com.apache.fastandroid.network.retrofit;

import com.apache.fastandroid.network.response.BaseResponse;


public interface ProtocolHandler<T> {

    void onSuccess(BaseResponse<T> protocol);

    void onFailure(int fail_code, String msg);
}