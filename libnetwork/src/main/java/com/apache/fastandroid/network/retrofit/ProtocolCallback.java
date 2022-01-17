package com.apache.fastandroid.network.retrofit;

import com.apache.fastandroid.network.response.BaseResponse;
import com.tesla.framework.common.util.log.NLog;

import retrofit2.Call;
import retrofit2.Response;




public abstract class ProtocolCallback<T> implements retrofit2.Callback<BaseResponse<T>>, ProtocolHandler<T> {
    private static final String TAG = "ProtocolCallback";

    private String url;

    public ProtocolCallback() {
    }

    public ProtocolCallback(String url) {
        this.url = url;
    }

    @Override
    public  void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
        if (!response.isSuccessful()) {
            onCallFailed(response.code(),null);
            return;
        }

        BaseResponse<T> body = response.body();
        //后台如果按照 {"code":0,"msg":"Success","data":{}} 而不是" {code":0,"msg":"Success","data":""} 来返回，理论上body是可能为空的
//        if (body == null || body.data == null) {
        //有些接口的data确实是返回为空的,例如编辑角色接口
        if (body == null) {
            onCallFailed(-101, "body is null");
            return;
        }

        if (!isSuccessful(body.getErrorCode())) {
            onCallFailed(body.getErrorCode(), body.getErrorMsg());
            return;
        }
//        NLog.i(TAG, "onResponse success code = %d, msg = %s,data: %s", body.code, body.msg,body.data);
        onSuccess(body);

        onFinish();
    }

    private void onCallFailed(int code, String msg){
        onFailure(code,msg);
        onFinish();
    }

    protected void onFinish() {
        NLog.d(TAG, "ProtocolCallback onFinish --->");
    }


    @Override
    public  void onFailure(Call<BaseResponse<T>> call, Throwable t) {


        NLog.i(TAG, "onResponse failure msg = %s", t.getMessage());
//        onFailure(err, t.getMessage());
        onCallFailed(304, t.getMessage());
    }

    protected boolean isSuccessful(int data_code) {
        return (data_code == 0
                || data_code == 200);
    }
}


//
