/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-08 01:01:18
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.apache.fastandroid.topic.bean;


import com.apache.fastandroid.artemis.support.bean.Abilities;
import com.apache.fastandroid.artemis.support.bean.User;
import com.tesla.framework.component.orm.annotation.PrimaryKey;

import java.io.Serializable;

/**
 * topic 简略信息
 */
public class TopicBean implements Serializable {
    @PrimaryKey(column = "id")
    public int id;                         // 唯一 id
    public String title;                   // 标题
    public String created_at;              // 创建时间
    public String updated_at;              // 更新时间
    public String replied_at;              // 最近一次回复时间
    public int replies_count;              // 回复总数量
    public String node_name;               // 节点名称
    public int node_id;                    // 节点 id
    public int last_reply_user_id;         // 最近一次回复的用户 id
    public String last_reply_user_login;   // 最近一次回复的用户登录名
    public User user;                      // 创建该话题的用户(信息)
    public boolean deleted;                // 是否是被删除的
    public boolean excellent;              // 是否是加精的
    public Abilities abilities;            // 当前用户对该话题拥有的权限



    @Override
    public boolean equals(Object obj) {
         if (!(obj instanceof TopicBean)) {
            return false;
        }
        TopicBean temp = (TopicBean) obj;
        if (temp.id == id) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}