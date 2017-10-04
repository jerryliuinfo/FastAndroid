package com.apache.fastandroid.artemis;

import com.apache.fastandroid.artemis.support.bean.BaseBean;
import com.apache.fastandroid.artemis.support.exception.ICheck;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.task.TaskException;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 01370340 on 2017/9/2.
 */

public abstract class BaseBizLogic extends ABizLogic {

    public <T extends BaseBean> T getResponseBody(Call<T> call) throws IOException, TaskException {
        if (call != null){
            Response<T> response = call.execute();
            if (response != null && response.isSuccessful()){
                BaseBean<T> baseBean = response.body();
                checkRepsonse(baseBean);

                return baseBean.getData();
            }
        }
        return null;

    }


    /**
     * 对服务器返回的数据做一些通用检查
     * @param result
     * @param <T>
     * @throws TaskException
     */
    public <T extends BaseBean> void checkRepsonse(T result)throws TaskException {
        if (result == null){
            throw new TaskException("数据为空");
        }
        if (result.getData() == null){
            throw new TaskException("data字段为空");
        }
        final BaseBean baseBean =  result;
        if (!"0".equals(baseBean.getCode())){
            TaskException taskException =  new TaskException(baseBean.getCode(),baseBean.getMsg());
            throw taskException;
        }
        //检查一些业务异常
        if (result.getData() instanceof ICheck){
            ((ICheck) result).check();
        }
    }


    public <T> T checkCallResult(Call<T> call) throws TaskException{
        if (call == null){
            throw new TaskException("call is null");
        }
        try {
            Response<T> response = call.execute();
            if (response == null){
                throw new TaskException("response is null");
            }
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
