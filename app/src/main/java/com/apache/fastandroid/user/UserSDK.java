package com.apache.fastandroid.user;

import com.apache.fastandroid.support.bean.BaseBean;
import com.apache.fastandroid.support.exception.ICheck;
import com.apache.fastandroid.user.bean.UserBean;
import com.apache.fastandroid.user.support.UserCenterHttpUtility;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/9/1.
 */

public class UserSDK extends ABizLogic {

    public static UserSDK newInstance(){
        return new UserSDK();
    }

    @Override
    protected HttpConfig configHttpConfig() {
        return new HttpConfig();
    }

    public UserBean doLogin(String userName, String pwd) throws TaskException{
        Setting setting = newSetting("doLogin","doLogin","登录");
        setting.getExtras().put(HTTP_UTILITY, newSettingExtra("doLogin",UserCenterHttpUtility.class.getName(),"登录"));

        Params params = new Params();
        params.addParameter("userName",userName);
        params.addParameter("pwd",pwd);


        UserBean result =  doGet(configHttpConfig(),setting,params,UserBean.class);
        checkResponseCode(result);
        if (result instanceof ICheck){
            ((ICheck) result).check();
        }
        return result;

    }

    private <T> boolean checkResponseCode(T result) throws TaskException {
        if (result == null){
            throw new TaskException("数据为空");
        }
        if (result instanceof BaseBean){
            BaseBean baseBean = (BaseBean) result;
            if (!"0".equals(baseBean.getCode())){
                TaskException taskException =  new TaskException(baseBean.getCode(),baseBean.getMsg());
                throw taskException;
            }
        }
        return true;
    }



}
