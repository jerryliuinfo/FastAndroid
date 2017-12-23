package com.apache.fastandroid.topic.cache;

import com.apache.fastandroid.topic.bean.TopicBean;
import com.apache.fastandroid.topic.bean.TopicBeans;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.component.orm.extra.Extra;
import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.network.cache.ICacheUtility;
import com.tesla.framework.network.http.Params;

import java.util.List;

import static com.apache.fastandroid.topic.cache.TopicDb.getDb;

/**
 * Created by 01370340 on 2017/12/22.
 */

public class TopicCacheUtility implements ICacheUtility {
    @Override
    public void addCache(Setting action, Params params, IResult iResult) {
        int offset = Integer.parseInt(params.getParameter("offset"));

        TopicBeans topicBeans = (TopicBeans) iResult;
        //如果是下拉刷新，则先清除之前的数据
        if (offset == 0){
            getDb().deleteAll(new Extra(null,null), TopicBean.class);
        }
        getDb().insert(new Extra(null,null),topicBeans.list);

        List<TopicBean> list = TopicDb.getDb().select(null,TopicBean.class);
        NLog.d("list size = %s", list.size());
    }

    @Override
    public IResult findCacheData(Setting action, Params params) {
        try {
            List<TopicBean> topicList = TopicDb.getDb().select(new Extra(null,null), TopicBean.class);
            if (!topicList.isEmpty()){
                TopicBeans topicBeans = new TopicBeans();
                topicBeans.setList(topicList);
                topicBeans.setFramCache(true);
                return topicBeans;
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}
