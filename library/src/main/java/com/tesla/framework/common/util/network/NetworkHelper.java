package com.tesla.framework.common.util.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tesla.framework.common.util.log.NLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络帮助模块
 * @author wenbiao.xie
 *
 */
public class NetworkHelper {

	private static String TAG = "NetworkHelper";
	boolean mRegistered = false;
	NetworkStatus mStatus = NetworkStatus.NetworkNotReachable;
	NetworkBroadcastReceiver mReceiver = new NetworkBroadcastReceiver();
	List<WeakReference<NetworkInductor>> mInductors;

	public enum NetworkStatus{
		NetworkNotReachable,
		NetworkReachableViaWWAN,
		NetworkReachableViaWiFi,
	}

	public interface NetworkInductor
	{
		void onNetworkChanged(NetworkStatus status);
	}
	
	private static NetworkHelper instance = null;
	public static NetworkHelper getInstance() {
	        if (instance == null) {
	            synchronized (NetworkHelper.class) {
	                if (instance == null){
	                    instance = new NetworkHelper();
	                }
	        }
	    }
	    return instance;
	}


	
	private NetworkHelper(){
		mInductors = new ArrayList<>();
	}
	
	public void registerNetworkSensor(Context context)
	{
		NLog.v(TAG, "registerNetworkSensor");
		if (mRegistered)
			return;
		
		mRegistered = true;
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info == null || !info.isAvailable())
		{
			NLog.i(TAG, "network not reachable");
			mStatus = NetworkStatus.NetworkNotReachable;
		}
		else if (info.getType() == ConnectivityManager.TYPE_MOBILE)
		{
			mStatus = NetworkStatus.NetworkReachableViaWWAN;
			
		}
		else if (info.getType() == ConnectivityManager.TYPE_WIFI)
		{
			mStatus = NetworkStatus.NetworkReachableViaWiFi;
		}
		

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(mReceiver, intentFilter);
	}
	
	public void unregisterNetworkSensor(Context context)
	{
		if (!mRegistered)
			return;
		
		mRegistered = false;
		context.unregisterReceiver(mReceiver);
	}
	
	public NetworkStatus getNetworkStatus() {
		return mStatus;
	}
	
	public boolean isWifiActive() {
		return mStatus.equals(NetworkStatus.NetworkReachableViaWiFi);
	}
	
	public boolean isNetworkAvailable() {
		return !mStatus.equals(NetworkStatus.NetworkNotReachable);
	}
	
	public void addNetworkInductor(NetworkInductor inductor)
	{
		final List<WeakReference<NetworkInductor>> list = new ArrayList<WeakReference<NetworkInductor>>(mInductors);
		for (int i = 0; i < list.size(); i++) {
			WeakReference<NetworkInductor> inductorRef = list.get(i);
			NetworkInductor ind = inductorRef.get();
			if ( ind == inductor)
				return;
			else if (ind == null) {
				mInductors.remove(inductorRef);
			}
		}
		
		mInductors.add(new WeakReference<>(inductor));
	}
	
	public void removeNetworkInductor(NetworkInductor inductor)
	{
		final List<WeakReference<NetworkInductor>> list = new ArrayList<WeakReference<NetworkInductor>>(mInductors);
		for (int i = 0; i < list.size(); i++) {
			WeakReference<NetworkInductor> inductorRef = list.get(i);
			NetworkInductor ind = inductorRef.get();
			if ( ind == inductor) {
				mInductors.remove(inductorRef);
				return;
			}else if (ind == null) {
				mInductors.remove(inductorRef);
			}
		}
	}
	
	protected void onNetworkChanged() {
		if (mInductors.size() == 0)
			return;
		
		final List<WeakReference<NetworkInductor>> list = new ArrayList<WeakReference<NetworkInductor>>(mInductors);		
		for (int i = 0; i < list.size(); i++) {
			WeakReference<NetworkInductor> inductorRef = list.get(i);
			NetworkInductor inductor = inductorRef.get();
			if (inductor != null) 
				inductor.onNetworkChanged(mStatus);
			else 
				mInductors.remove(inductorRef);
		}
	}
	
	protected class NetworkBroadcastReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive (Context context, Intent intent)
		{
			
			NLog.v("NetworkBroadcastReceiver", "onReceive");
			if (intent == null)
				return;

			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION))
			{
				ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = manager.getActiveNetworkInfo();
				NetworkStatus ns = NetworkStatus.NetworkNotReachable;
				if (info == null || !info.isAvailable())
				{
					NLog.i("NetworkBroadcastReceiver", "network not reachable");
					ns = NetworkStatus.NetworkNotReachable;
				}
				else if (info.getType() == ConnectivityManager.TYPE_MOBILE)
				{
					NLog.i("NetworkBroadcastReceiver", "network reachable via wwan");
					ns = NetworkStatus.NetworkReachableViaWWAN;			
					
				}
				else if (info.getType() == ConnectivityManager.TYPE_WIFI)
				{
					NLog.i("NetworkBroadcastReceiver", "network reachable via wifi");
					ns = NetworkStatus.NetworkReachableViaWiFi;
				}
				
				if (!mStatus.equals(ns)) {
					mStatus = ns;
					onNetworkChanged();
				}
			}
		}
	}
	

}
