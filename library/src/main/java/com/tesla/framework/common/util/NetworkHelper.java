package com.tesla.framework.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 网络帮助模块
 * @author wenbiao.xie
 *
 */
public class NetworkHelper {

	private static String TAG = "NetworkHelper";
	public enum NetworkStatus{
		NetworkNotReachable,
		NetworkReachableViaWWAN,
		NetworkReachableViaWiFi,
	}
	
	public interface NetworkInductor
	{
		void onNetworkChanged(NetworkStatus status);
	}
	
	private static class HelperHolder {
		private static final NetworkHelper helper = new NetworkHelper();
	}
	
	public static NetworkHelper sharedHelper()
	{
		return HelperHolder.helper;
	}
	boolean mRegistered = false;
	NetworkStatus mStatus = NetworkStatus.NetworkNotReachable;
	private boolean is4G = false;
	NetworkBroadcastReceiver mReceiver = new NetworkBroadcastReceiver();
	List<WeakReference<NetworkInductor>> mInductors;
	
	private NetworkHelper(){
		//load();		
		mInductors = new LinkedList<>();
	}
	
	public void registerNetworkSensor(Context context)
	{
		if (mRegistered || context == null)
			return;
		
		mRegistered = true;

		try {
			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = null;
			info = manager.getActiveNetworkInfo();
			if (info == null || !info.isAvailable())
			{
				mStatus = NetworkStatus.NetworkNotReachable;
			}
			else if (info.getType() == ConnectivityManager.TYPE_MOBILE)
			{
				mStatus = NetworkStatus.NetworkReachableViaWWAN;
				is4G = (info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE);
			}
			else if (info.getType() == ConnectivityManager.TYPE_WIFI)
			{
				mStatus = NetworkStatus.NetworkReachableViaWiFi;
			}

			//native_set_network_status(mStatus.ordinal());

			IntentFilter intentFilter = new IntentFilter();
			intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
			context.registerReceiver(mReceiver, intentFilter);
		} catch (Exception e) {
			mRegistered = false;
		}catch (Throwable e) {
			mRegistered = false;
		}

	}
	
//	public void unregisterNetworkSensor(Context context)
//	{
//		if (!mRegistered)
//			return;
//
//		mRegistered = false;
//		context.unregisterReceiver(mReceiver);
//	}
//
//	public NetworkStatus getNetworkStatus() {
//		return mStatus;
//	}
	
	public boolean isWifiActive() {
		return mStatus.equals(NetworkStatus.NetworkReachableViaWiFi);
	}

	public boolean is4G() {
		return isMobileActive() && is4G;
	}

	public boolean isMobileActive() {
		return mStatus.equals(NetworkStatus.NetworkReachableViaWWAN);
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
		
		mInductors.add(new WeakReference<NetworkInductor>(inductor));
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
			try {
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
						ns = NetworkStatus.NetworkNotReachable;
					}
					else if (info.getType() == ConnectivityManager.TYPE_MOBILE)
					{
						ns = NetworkStatus.NetworkReachableViaWWAN;
						is4G = (info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE);
					}
					else if (info.getType() == ConnectivityManager.TYPE_WIFI)
					{
						ns = NetworkStatus.NetworkReachableViaWiFi;
					}

					if (!mStatus.equals(ns)) {
						mStatus = ns;
						//native_set_network_status(mStatus.ordinal());
						onNetworkChanged();
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}

		}
	}
	
	//private native static void native_set_network_status(int status);

	// 由于网络状态埋点，这套按理可以写到公共埋点数据Bean中的
	private final static String WIFI = "1";
	private final static String MOBILE_4G = "2";
	private final static String MOBILE_ELSE = "3";
	private final static String NO_NETWORK = "4";
	private final static String UNKNOWN = "5";

	public String getNetworkStateCode() {
		NetworkStatus status = mStatus;
		switch (status) {
			case NetworkReachableViaWiFi:
				return WIFI;
			case NetworkReachableViaWWAN:
				if (is4G) {
					return MOBILE_4G;
				} else {
					return MOBILE_ELSE;
				}
			case NetworkNotReachable:
				return NO_NETWORK;
			default:
				return UNKNOWN;
		}
	}
}
