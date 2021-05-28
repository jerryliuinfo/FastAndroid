package com.tesla.framework.common.util.network;

/**
 * author: 01370340
 * data: 2019/5/17
 * description:
 */
public interface NetworkListener {

    void onConnected(NetworkType networkType);

    void onDisconnected();


}
