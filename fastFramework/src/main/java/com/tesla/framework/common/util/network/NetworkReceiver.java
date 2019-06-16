package com.tesla.framework.common.util.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * author: 01370340
 * data: 2019/5/17
 * description:
 */
public class NetworkReceiver extends BroadcastReceiver {
    private static final String ACTION_NETWORK_CHANGED = "android.net.conn.CONNECTIVITY_CHANGE";

    private NetworkListener mListener;

    private NetworkType mNetworkType;

    public NetworkReceiver(){
        mNetworkType = NetworkType.NONE;
    }


    public void setListener(NetworkListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null){
            return;
        }
        if (ACTION_NETWORK_CHANGED.equals(intent.getAction())){
            mNetworkType = null;
            if (NetworkHelper.isNetworkAvailable(context)){
                if (mListener != null){
                    mListener.onConnected(mNetworkType);
                }
            }else {
                if (mListener != null){
                    mListener.onDisconnected();
                }
            }

        }
    }


}
