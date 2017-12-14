package com.apache.fastandroid.support.sdk;

import com.apache.fastandroid.support.bean.MarathonBean;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.task.TaskException;

/**
 * Created by 01370340 on 2017/12/14.
 */

public class MarathonSdk extends ABizLogic{
    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig config = new HttpConfig();
        //http://10.2.4.15:8080/sf-plab-web/category/test
        //config.baseUrl = "http://10.2.4.15:8080/";
        config.baseUrl = "http://10.2.4.31:8080/";
        return config;
    }

    public static MarathonSdk newInstance(){
        return new MarathonSdk();
    }

    /**
     * http://10.2.4.15:8080/sf-plab-web/category/test
     * @return
     * @throws TaskException
     */
    public String getCategory()throws TaskException{
        Setting action = newSetting("getCategory","sf-plab-web/category/test","getCategory");
        return doGet(getHttpConfig(),action,null,String.class);
    }
    public String getCategoryByPost()throws TaskException{
        Setting action = newSetting("getCategory","sf-plab-web/category/test","getCategory");
        return doPost(getHttpConfig(),action,null,null,null,String.class);
    }


    public String getCategory2()throws TaskException{
        Setting action = newSetting("getCategory","pentaKill/test/1","getCategory");
        return doGet(getHttpConfig(),action,null,String.class);
    }



    public MarathonBean testMarathon()throws TaskException{
        Setting action = newSetting("testMarathon","pentaKill/test/1","testMarathon");
        return doGet(getHttpConfig(),action,null,MarathonBean.class);
    }
}

