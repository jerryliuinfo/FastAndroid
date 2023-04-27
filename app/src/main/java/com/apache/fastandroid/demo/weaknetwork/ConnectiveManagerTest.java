package com.apache.fastandroid.demo.weaknetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.blankj.utilcode.util.Utils;
import com.tesla.framework.component.logger.Logger;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by Jerry on 2023/4/23.
 */
public class ConnectiveManagerTest implements INetworkTest{
   @Override
   public boolean testNetwork() {
      ConnectivityManager connectivityManager = (ConnectivityManager) Utils.getApp().getSystemService(CONNECTIVITY_SERVICE);

      if (connectivityManager != null) {
         NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

         if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            int subType = networkInfo.getSubtype();

            if (type == ConnectivityManager.TYPE_WIFI) {
               WifiManager wifiManager = (WifiManager) Utils.getApp().getSystemService(Context.WIFI_SERVICE);
               int linkSpeed = wifiManager.getConnectionInfo().getLinkSpeed();
               Logger.d("ConnectiveManagerTest TYPE_WIFI linkSpeed:%s", linkSpeed);
               // linkSpeed is in Mbps
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
               Logger.d("ConnectiveManagerTest TYPE_MOBILE subType:%s", subType);

               switch (subType) {
                  case TelephonyManager.NETWORK_TYPE_1xRTT:
                     // 50-100 kbps
                     break;
                  case TelephonyManager.NETWORK_TYPE_CDMA:
                     // 14-64 kbps
                     break;
                  case TelephonyManager.NETWORK_TYPE_EDGE:
                     // 50-100 kbps
                     break;
                  case TelephonyManager.NETWORK_TYPE_EVDO_0:
                     // 400-1000 kbps
                     break;
                  case TelephonyManager.NETWORK_TYPE_EVDO_A:
                     // 600-1400 kbps
                     break;
                  case TelephonyManager.NETWORK_TYPE_GPRS:
                     // 100 kbps
                     break;
                  case TelephonyManager.NETWORK_TYPE_HSDPA:
                     // 2-14 Mbps
                     break;
                  case TelephonyManager.NETWORK_TYPE_HSPA:
                     // 700-1700 kbps
                     break;
                  case TelephonyManager.NETWORK_TYPE_HSUPA:
                     // 1-23 Mbps
                     break;
                  case TelephonyManager.NETWORK_TYPE_UMTS:
                     // 400-7000 kbps
                     break;
               }
            }
         }
      }
      return false;
   }
}
