package com.apache.fastandroid.artemis.retrofit;

import com.tesla.framework.network.task.TaskException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 01370340 on 2017/12/14.
 */

public class RetrofitUtil {
    /**
     * 对Retrofit的call做数据解析
     * @param call
     * @param <T>
     * @return
     * @throws TaskException
     */
    public static <T> T checkCallResult(Call<T> call) throws TaskException {
        try {
            Response<T> response = call.execute();
            if (!response.isSuccessful()){
                throw new TaskException("response.isSuccessful() = false");
            }
            T t =  response.body();
            if (t == null){
                throw new TaskException("response body is null");
            }
            return t;
        } catch (IOException e) {
            e.printStackTrace();
            throw new TaskException(e.getMessage());
        }
    }
}
