package com.apache.fastandroid.artemis.rx;

import com.apache.fastandroid.artemis.support.bean.BaseBean;
import com.apache.fastandroid.artemis.support.exception.ICheck;
import com.tesla.framework.network.task.TaskException;

import rx.Subscriber;

/**
 * Created by 01370340 on 2017/9/4.
 */

public abstract class HttpResultSubsriber<T> extends Subscriber<BaseBean<T>> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(BaseBean<T> result) {
        if (result == null){
            _onError(new TaskException("数据为空"));
            return;
        }
        if (result.getData() == null){
            _onError(new TaskException("data字段为空"));
            return;
        }
        //检查code
        if (result instanceof BaseBean){
            BaseBean baseBean = (BaseBean) result;
            if (!"0".equals(baseBean.getCode())){
                TaskException taskException =  new TaskException(baseBean.getCode(),baseBean.getMsg());
                _onError(taskException);
            }
        }
        //检查一些业务异常
        if (result.getData() instanceof ICheck){
            try {
                ((ICheck) result).check();
            } catch (TaskException e) {
                e.printStackTrace();
                _onError(e);
            }
        }

    }

    public abstract void _onSuccess(T t);

    public abstract void _onError(Throwable e);
}
