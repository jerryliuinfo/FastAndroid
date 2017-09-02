package com.apache.fastandroid.user.support;

import com.apache.fastandroid.user.bean.UserBean;
import com.apache.fastandroid.user.bean.UserResponseBean;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.IHttpUtility;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/9/1.
 */

public class UserCenterHttpUtility implements IHttpUtility {

    @Override
    public <T> T doGet(HttpConfig config, Setting action, Params urlParams, Class<T> responseCls) throws TaskException {
        if ("doLogin".equals(action.getType())){

            String userName = urlParams.getParameter("userName");
            String pwd = urlParams.getParameter("pwd");

            UserResponseBean result = new UserResponseBean();

            UserBean data = new UserBean();
            data.setUserName(userName);
            data.setPassword(pwd);
            if ("zhangsan".equals(userName) && "123456".equals(pwd)){
                result.setCode("0");
                //userBean.setToken("invalid");
            }else {
                result.setCode("100");
            }
            result.data = data;
            return (T) result;


        }

        return null;
    }

    @Override
    public <T> T doPost(HttpConfig config, Setting action, Params urlParams, Params bodyParams, Object requestObj,
                        Class<T> responseCls) throws TaskException {
        return null;
    }

    @Override
    public <T> T doPostFiles(HttpConfig config, Setting action, Params urlParams, Params bodyParams, MultipartFile[] files, Class<T> responseCls) throws TaskException {
        return null;
    }
}
