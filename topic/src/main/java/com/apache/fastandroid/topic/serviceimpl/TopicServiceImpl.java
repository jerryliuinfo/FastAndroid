package com.apache.fastandroid.topic.serviceimpl;

import android.support.v4.app.Fragment;
import com.apache.fastandroid.artemis.componentService.topic.ITopicService;
import com.apache.fastandroid.topic.TopicTabsFragment;

/**
 * author: 01370340
 * data: 2019/6/17
 * description:
 */
public class TopicServiceImpl implements ITopicService {
    @Override
    public Fragment getTopicTabsFragment() {
        return TopicTabsFragment.newFragment();
    }
}
