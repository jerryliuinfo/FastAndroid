package com.apache.fastandroid.demo.weaknetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Jerry on 2023/4/23.
 */
class NetworkMonitor {
   public static final String TAG = NetworkMonitor.class.getSimpleName();


   private static final String CMD_PING = "/system/bin/ping -c 5 "; //执行5次ping命令
   private static final String GOOGLE_DNS = "8.8.8.8"; //谷歌DNS地址


   /**
    * 基于主动探测的技术
    * 使用 Ping 命令来测试网络的响应时间和丢包率。具体实现过程如下
    *
    * @param context
    * @return
    */

   public static boolean isNetworkAvailable(Context context) {
      try {
         Process process = Runtime.getRuntime().exec(CMD_PING + GOOGLE_DNS);
         int exitCode = process.waitFor();
         return (exitCode == 0);
      } catch (IOException | InterruptedException e) {
         e.printStackTrace();
      }
      return false;
   }


   /**
    * 基于主动探测的技术
    * 使用 HttpURLConnection 类来向服务器发送数据包（如 ICMP 包、UDP 包等），并记录每次发送和接收的时间戳、丢包率等信息
    */
   public static void checkNetworkAvailability() {
      try {
         URL url = new URL("http://www.google.com");
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setConnectTimeout(10000); //10秒超时
         conn.setRequestMethod("GET");
         int responseCode = conn.getResponseCode();
         if (responseCode == HttpURLConnection.HTTP_OK) {
            Log.d(TAG, "Network is available.");
         } else {
            Log.d(TAG, "Network is not available.");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }


   /**
    * 基于被动观察的技术
    * 使用 ConnectivityManager 和 NetworkCapabilities 类来获取网络状态，并根据网络状态判断当前是否处于弱网状态。
    *
    * @param context
    * @return
    */
   public static boolean isWeakNetwork(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager)
              context.getSystemService(CONNECTIVITY_SERVICE);
      if (connectivityManager != null) {
         NetworkCapabilities networkCapabilities =
                 connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
         if (networkCapabilities != null) {
            int signalStrength = networkCapabilities.getSignalStrength();
            if (signalStrength < 0) { //信号强度为负数时表示处于弱网状态
               return true;
            }
         }
      }
      return false;
   }


   /*public static void isSlowNetwork(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
         int type = activeNetworkInfo.getType();
         if (type == ConnectivityManager.TYPE_WIFI) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int linkSpeed = wifiInfo.getLinkSpeed(); //获取连接速度
            if (linkSpeed < 10) { //如果连接速度小于10Mbps，则认为网络缓慢
               // 网络缓慢的操作
            }
         } else if (type == ConnectivityManager.TYPE_MOBILE) {
            int subType = activeNetworkInfo.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_2G || subType == TelephonyManager.NETWORK_TYPE_UNKNOWN) { //如果是2G或未知网络类型，则认为网络缓慢
               // 网络缓慢的操作
            }
         }
      }

   }*/
}
