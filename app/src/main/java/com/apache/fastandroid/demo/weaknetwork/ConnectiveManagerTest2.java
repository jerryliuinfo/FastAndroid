package com.apache.fastandroid.demo.weaknetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

import com.blankj.utilcode.util.Utils;
import com.tesla.framework.component.logger.Logger;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jerry on 2023/4/23.
 */
public class ConnectiveManagerTest2 implements INetworkTest{
   @Override
   public boolean testNetwork() {
      ConnectivityManager connMgr = (ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
      boolean isConnected = networkInfo != null && networkInfo.isConnected();

      if (isConnected) {
         NetworkCapabilities networkCapabilities = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());
         boolean hasTransportCellular = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
         boolean hasTransportWifi = networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);




         if (hasTransportCellular || hasTransportWifi) {
            try {
               URL url = new URL("https://baidu.com");
               HttpURLConnection connection = null;
               connection = (HttpURLConnection) url.openConnection();
               connection.connect();

               long startTime = System.currentTimeMillis();
               InputStream inputStream = connection.getInputStream();

               byte[] buffer = new byte[1024];
               int bytesRead;
               long totalBytesRead = 0;
               while ((bytesRead = inputStream.read(buffer)) != -1) {
                  totalBytesRead += bytesRead;
               }

               long endTime = System.currentTimeMillis();
               long duration = endTime - startTime;

               //返回的网络速度单位是 Mbps（兆比特每秒）
               float networkSpeed = (float) totalBytesRead / duration;
               Logger.d("ConnectiveManagerTest speed:%s", networkSpeed);

               // 获取网络速度，单位为 Mbps
               int speedMbps = networkCapabilities.getLinkDownstreamBandwidthKbps() / 1000;
               Logger.d("ConnectiveManagerTest speed2:%s", speedMbps);


               if(networkSpeed < 1) {
                  // 网络较慢，可能属于弱网
               } else if (networkSpeed >=1 && networkSpeed <= 5) {
                  // 网络不太快，但也不算太慢
               } else if (networkSpeed > 5 && networkSpeed <= 10) {
                  // 网络速度还不错
               } else {
                  // 网络速度很快
               }

               inputStream.close();
               connection.disconnect();
            }

            catch (Exception e) {
               throw new RuntimeException(e);
            }
         }
      }
      return false;
   }
}
