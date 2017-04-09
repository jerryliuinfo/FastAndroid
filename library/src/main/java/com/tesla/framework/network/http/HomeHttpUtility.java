package com.tesla.framework.network.http;

import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.support.bean.HomeBean;
import com.tesla.framework.support.bean.HomeBeans;

import java.util.ArrayList;

/**
 * Created by jerryliu on 2017/4/8.
 */

public class HomeHttpUtility implements IHttpUtility {
    @Override
    public <T> T doGet(HttpConfig config, Setting action, Params urlParams, Class<T> responseCls) throws TaskException {
        if ("loadPerson".equals(action.getType())){
            HomeBeans result = new HomeBeans();

            int index = Integer.parseInt(urlParams.getParameter("index"));
            int pageSize = Integer.parseInt(urlParams.getParameter("pageSize"));
            if (index >= 5){
                return (T) result;
            }

            ArrayList<HomeBean> persons = new ArrayList<>();
            HomeBean person;
            for (int i = 0; i < pageSize; i++){
                person = new HomeBean("name:"+(index * pageSize + i), "age: "+ (index * pageSize + i));
                persons.add(person);
            }
            result.beans = persons;
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
