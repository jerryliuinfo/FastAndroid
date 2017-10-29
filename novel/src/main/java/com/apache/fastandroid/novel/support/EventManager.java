package com.apache.fastandroid.novel.support;

import com.apache.fastandroid.novel.support.event.RefreshCollectionListEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 01370340 on 2017/10/27.
 */

public class EventManager {

    public static void refreshCollectionList(){
        EventBus.getDefault().post(new RefreshCollectionListEvent());
    }

}
