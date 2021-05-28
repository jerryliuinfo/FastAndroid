package com.tesla.framework.common.util;

import android.app.ActivityManager;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.alibaba.fastjson.util.IOUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jerryliu on 2017/6/10.
 */

public class DeviceUtil {

    private static final String CMCC_ISP = "46000";//中国移动
    private static final String CMCC2_ISP = "46002";//中国移动
    private static final String CU_ISP = "46001";//中国联通
    private static final String CT_ISP = "46003";//中国电信



    /**
     * 获取设备的系统版本号
     */
    public static int getDeviceSDK() {
        int sdk = Build.VERSION.SDK_INT;
        return sdk;
    }


    public static String getDeviceModel(){
        return "";
    }

    public static String getIMSI(Context context) {
        String result = "";
        try{
            TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            result =  telManager.getSubscriberId();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String IMEI = telephonyManager.getDeviceId();
            return IMEI;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取手机网络运营商类型
     *
     * @param context
     * @return
     */
    public static String getPhoneISP(Context context) {
        if (context == null) {
            return "";
        }
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String teleCompany = "";
        String np = manager.getNetworkOperator();

        if (np != null) {
            if (np.equals(CMCC_ISP) || np.equals(CMCC2_ISP)) {
                teleCompany = "中国移动";
            } else if (np.startsWith(CU_ISP)) {
                teleCompany = "中国联通";
            } else if (np.startsWith(CT_ISP)) {
                teleCompany = "中国电信";
            }
        }
        return teleCompany;
    }

    /**
     * mac地址
     *
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && wifiInfo.getMacAddress() != null)
            return wifiInfo.getMacAddress().replace(":", "");
        else
            return "0022f420d03f";// 00117f29d23a
    }


    public static String getUDPIP(Context context) {

        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi.getDhcpInfo();
        int IpAddress =dhcpInfo.ipAddress;
        int subMask = dhcpInfo.netmask;
        return transformIp((~subMask) | IpAddress);
    }

    private static String transformIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    public static String getIP(Context context) {
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return transformIp(wifi.getConnectionInfo().getIpAddress());
    }

    public static String getOS() {
        return "Android";
    }

    /**
     * 获得手机品牌
     * @return
     */
    public static String getPhoneBrand(){
        return Build.BRAND;
    }

    /**
     * 获取手机机型信息
     *
     * @return
     */
    public static String getModel() {
        return Build.MODEL == null ? "UNKNOWN" : Build.MODEL;
    }

    public static String getVersion() {
        return Build.VERSION.RELEASE == null ? "UNKNOWN" : Build.VERSION.RELEASE;
    }

    /**
     * 获得系统版本名称
     * @return
     */
    public static String getOsVersionName(){
        return Build.VERSION.RELEASE;
    }

    public static String getManufacturer() {
        return Build.MANUFACTURER == null ? "UNKNOWN" : Build.MANUFACTURER;
    }

    /**
     * 获取手机的Android_ID
     * @return
     */
    public static String getAndroidId(Context context){
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /**
     * 获取手机唯一序列号
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        String id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        //在某些机型上总是返回9774d56d682e549c这个值
        if ("9774d56d682e549c".equals(id)) {
            id = makeDeviceInfo();
        }
        try {
            return UUID.nameUUIDFromBytes(id.getBytes("utf8")).toString();
        } catch (UnsupportedEncodingException e) {
        }
        return id;
    }

    public static String makeDeviceInfo() {
        StringBuilder builder = new StringBuilder();
        builder .append(Build.BOARD).append("#")
                .append(Build.BRAND).append("#")
                .append(Build.DEVICE).append("#")
                .append(Build.DISPLAY).append("#")
                .append(Build.HOST).append("#")
                .append(Build.ID).append("#")
                .append(Build.MANUFACTURER).append("#")
                .append(Build.MODEL).append("#")
                .append(Build.PRODUCT).append("#")
                .append(Build.TAGS).append("#")
                .append(Build.TYPE).append("#")
                .append(Build.USER).append("#");
        return builder.toString();
    }





    public static int getCpuCount(){
        return Runtime.getRuntime().availableProcessors();
    }


    private static long getTotalRAM2() {
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            String line = reader.readLine();

            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(line);
            String value = "";
            while (m.find()) {
                value = m.group(1);
            }
            try {
                return Long.parseLong(value) / 1024;//KB转换成M
            } catch (NumberFormatException e) {
            }
        } catch (IOException e) {
        } finally {
            IOUtils.close(reader);
        }

        return 0;
    }

    /**
     * 获取总内存，单位M
     *
     * @param context
     * @return
     */
    public static long getTotalRAM(Context context) {
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(memoryInfo);
            return memoryInfo.totalMem / _1M;

        } else {
            return getTotalRAM2();
        }
    }


    private static long _1M = 1024 * 1024;


    /**
     * 获取当前已用内存，单位M
     *
     * @param context
     * @return
     */
    public static long getCurrentRAM(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        return getTotalRAM(context) - memoryInfo.availMem / _1M;
    }

    public static long maxMemory(){
        long maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("TAG", "Max memory is " + maxMemory + "KB");
        return maxMemory;
    }



}
