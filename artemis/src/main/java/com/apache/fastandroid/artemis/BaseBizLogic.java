package com.apache.fastandroid.artemis;

import com.apache.fastandroid.artemis.support.bean.BaseBean;
import com.apache.fastandroid.artemis.support.exception.ICheck;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/9/2.
 */

public abstract class BaseBizLogic extends ABizLogic {

    public <T extends BaseBean> void checkRepsonse(T result)throws TaskException {
        if (result == null){
            throw new TaskException("数据为空");
        }
        if (result.getData() == null){
            throw new TaskException("data字段为空");
        }
        if (result instanceof BaseBean){
            BaseBean baseBean = (BaseBean) result;
            if (!"0".equals(baseBean.getCode())){
                TaskException taskException =  new TaskException(baseBean.getCode(),baseBean.getMsg());
                throw taskException;
            }
        }
        //检查一些业务异常
        if (result.getData() instanceof ICheck){
            ((ICheck) result).check();
        }
    }
}
