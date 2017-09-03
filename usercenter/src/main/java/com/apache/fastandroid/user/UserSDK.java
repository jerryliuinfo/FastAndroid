package com.apache.fastandroid.user;

import com.apache.fastandroid.artemis.BaseBizLogic;
import com.apache.fastandroid.artemis.comBridge.IActionDelegate;
import com.apache.fastandroid.artemis.support.bean.UserBean;
import com.apache.fastandroid.user.bean.UserResponseBean;
import com.apache.fastandroid.user.support.UserCenterHttpUtility;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/9/1.
 */

public class UserSDK extends BaseBizLogic {

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


        UserResponseBean result =  doGet(configHttpConfig(),setting,params,UserResponseBean.class);
        checkRepsonse(result);
        if (result.data == null){
            throw new TaskException("data字段为空");
        }
        return result.data;
    }



    public boolean canAutoLogin(){
        if (!UserConfigManager.getInstance().isLastTimeLogined()){
            return false;
        }
        UserBean userBean = UserConfigManager.getInstance().getUserBean();
        try {
            UserBean loginResult =  doLogin(userBean.getUserName(),userBean.getPassword());
            if (loginResult != null){
                return true;
            }
        } catch (TaskException e) {
            e.printStackTrace();
        }
        return   false;
    }


    public void doAutoLogin(IActionDelegate.IActionCallback callback) throws TaskException{
        if (!UserConfigManager.getInstance().isLastTimeLogined()){
            callback.onActionFailed(-1, "");
            callback.onActionFinish();
            return ;
        }else {
            UserBean userBean = UserConfigManager.getInstance().getUserBean();
            boolean result = false;
            UserBean loginResult = null;
            try {
                loginResult =  doLogin(userBean.getUserName(),userBean.getPassword());
                if (loginResult != null){
                    result = true;
                }
            } catch (TaskException e) {
                e.printStackTrace();

            }
            if (result){
                callback.onActionSuccess(loginResult);

            }else {
                callback.onActionFailed(-1, "");
            }
            callback.onActionFinish();
        }



    }




}
