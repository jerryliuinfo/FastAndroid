package com.tesla.framework.common.util.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

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
		final List<WeakReference<NetworkInductor>> list = new ArrayList<>(mInductors);
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
		if (mInductors == null || mInductors.size() == 0)
			return;
		
		final List<WeakReference<NetworkInductor>> list = new ArrayList<>(mInductors);
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



	public static NetworkStatus getNetworkType(Context context) {

		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null) {
			switch (networkInfo.getType()) {
				case ConnectivityManager.TYPE_MOBILE:
					return NetworkStatus.NetworkReachableViaWWAN;
				case ConnectivityManager.TYPE_WIFI:
					return NetworkStatus.NetworkReachableViaWiFi;
			}
		}

		return NetworkStatus.NetworkNotReachable;
	}


	public static boolean isNetworkAvailable(Context context){
		return getNetworkType(context) != NetworkStatus.NetworkNotReachable;
	}



	/**
	 * 打开网络设置界面
	 * <p>3.0以下打开设置界面</p>
	 *
	 * @param context 上下文
	 */
	public static void openWirelessSettings(Context context) {
		if (android.os.Build.VERSION.SDK_INT > 10) {
			context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
		} else {
			context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		}
	}


	/**
	 * 判断网络是否是4G
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission
	 * .ACCESS_NETWORK_STATE"/>}</p>
	 *
	 * @param context 上下文
	 * @return {@code true}: 是<br>{@code false}: 不是
	 */
	public static boolean is4G(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager
				.NETWORK_TYPE_LTE;
	}

	/**
	 * 判断wifi是否连接状态
	 * <p>需添加权限 {@code <uses-permission android:name="android.permission
	 * .ACCESS_NETWORK_STATE"/>}</p>
	 *
	 * @param context 上下文
	 * @return {@code true}: 连接<br>{@code false}: 未连接
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm != null && cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
	}

	/**
	 * 获取移动网络运营商名称
	 * <p>如中国联通、中国移动、中国电信</p>
	 *
	 * @param context 上下文
	 * @return 移动网络运营商名称
	 */
	public static String getNetworkOperatorName(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm != null ? tm.getNetworkOperatorName() : null;
	}

	/**
	 * 获取当前连接wifi的名称
	 *
	 * @return
	 */
	public static String getConnectWifiSsid(Context context) {
		if (isWifiConnected(context)) {
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			return wifiInfo.getSSID();
		}
		return null;
	}

	/**
	 * 获取当前连接wifi的名称
	 *
	 * @return
	 */
	public static String getConnectWifiIp(Context context) {
		if (isWifiConnected(context)) {
			WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
			int ipAddress = wifiInfo.getIpAddress();
			if (ipAddress == 0) {
				return null;
			}
			return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
					+ (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
		}
		return null;
	}


}
