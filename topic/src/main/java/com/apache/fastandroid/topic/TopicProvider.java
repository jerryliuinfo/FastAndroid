package com.apache.fastandroid.topic;

import com.apache.fastandroid.artemis.modularization.provider.ProtocalA;
import com.apache.fastandroid.artemis.modularization.provider.SimpleProvider;

/**
 * author: 01370340
 * data: 2019/6/13
 * description:
 */
public class TopicProvider  extends SimpleProvider implements ProtocalA {

    @Override
    public String getUserA(String userId) {
        return "Hello Jerry";
    }
}
