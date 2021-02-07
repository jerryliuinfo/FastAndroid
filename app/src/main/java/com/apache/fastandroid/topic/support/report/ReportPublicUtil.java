package com.apache.fastandroid.topic.support.report;

import java.util.HashMap;

import android.content.Context;

import com.apache.fastandroid.topic.support.config.PublishVersionManager;
import com.blankj.utilcode.util.ScreenUtils;
import com.tesla.framework.common.util.DeviceUtil;

/**
 * Created by jerryliu on 2017/6/9.
 * 上报埋点公共字段
 */

public class ReportPublicUtil {
    public final static String UUID = "uuid";//客户端IMEI号
    public final static String VER = "ver";//程序版本号
    public final static String MCC = "mcc";//移动国家号码
    public final static String LANGUAGE = "lang";//系统语言
    public final static String SOURCE = "source"; //渠道号

    public final static String OSVER = "osver";
    public final static String AREA = "area";
    public final static String BRAND = "brand";//手机品牌
    public final static String MODEL = "model"; //手机型号

    public final static String SCREEN_WIDTH = "width"; //屏幕宽度
    public final static String SCREEN_HEIGHT = "height"; //屏幕高度
    public final static String SCREEN_DPI = "dpi"; //分辨率
    public final static String RAM = "ram"; //分辨率


    public final static String TIME="time";//客户端日志产生时的时间


    //埋点上报公共字段
    private static HashMap<String,String> mBasicPublicParamMap = new HashMap<>();

    public static void init(Context context){
        initUUID();
        initVER();
        initMCC(context);
        initLang();
        initSource();
        initOsVer();
        initArea();
        initBrand();
        initModel();
        initScreenWidth(context);
        initScreenHeight(context);
        initScreenDpi(context);
        initRam();
    }

    static {

    }

    private static void initUUID(){
        mBasicPublicParamMap.put(UUID, "");
    }

    private static void initVER(){
        mBasicPublicParamMap.put(VER, PublishVersionManager.getVersionName() + "."+PublishVersionManager.getVersionCode());
    }

    private static void initMCC(Context context){
        mBasicPublicParamMap.put(MCC, DeviceUtil.getIMSI(context));
    }

    private static void initLang(){
        mBasicPublicParamMap.put(LANGUAGE, "");
    }

    private static void initSource(){
        mBasicPublicParamMap.put(SOURCE, PublishVersionManager.getChannelId());
    }

    private static void initOsVer(){
        mBasicPublicParamMap.put(OSVER, DeviceUtil.getOsVersionName());
    }

    private static void initArea(){
        mBasicPublicParamMap.put(AREA, "");
    }

    private static void initBrand(){
        mBasicPublicParamMap.put(BRAND, DeviceUtil.getPhoneBrand());
    }

    private static void initModel(){
        mBasicPublicParamMap.put(MODEL, DeviceUtil.getDeviceModel());
    }

    private static void initScreenWidth(Context context){
        mBasicPublicParamMap.put(SCREEN_WIDTH, String.valueOf(ScreenUtils.getScreenWidth()));
    }

    private static void initScreenHeight(Context context){
        mBasicPublicParamMap.put(SCREEN_HEIGHT, String.valueOf(ScreenUtils.getScreenHeight()));
    }

    private static void initScreenDpi(Context context){
        mBasicPublicParamMap.put(SCREEN_DPI, ScreenUtils.getScreenWidth() +"-" + ScreenUtils.getScreenHeight());
    }

    private static void initRam(){
        mBasicPublicParamMap.put(RAM, "");
    }




    public static String getUUID() {
        return mBasicPublicParamMap.get(UUID);
    }

    public static String getVer() {
        return mBasicPublicParamMap.get(VER);
    }

    public static String getMcc() {
        return mBasicPublicParamMap.get(MCC);
    }

    public static String getLanguage() {
        return mBasicPublicParamMap.get(LANGUAGE);
    }

    public static String getSource() {
        return mBasicPublicParamMap.get(SOURCE);
    }

    public static String getOsver() {
        return mBasicPublicParamMap.get(OSVER);
    }

    public static String getArea() {
        return mBasicPublicParamMap.get(AREA);
    }

    public static String getBrand() {
        return mBasicPublicParamMap.get(BRAND);
    }

    public static String getModel() {
        return mBasicPublicParamMap.get(MODEL);
    }

    public static String getScreenWidth() {
        return mBasicPublicParamMap.get(SCREEN_WIDTH);
    }

    public static String getScreenHeight() {
        return mBasicPublicParamMap.get(SCREEN_HEIGHT);
    }

    public static String getScreenDpi() {
        return mBasicPublicParamMap.get(SCREEN_DPI);
    }

    public static String getRam() {
        return mBasicPublicParamMap.get(RAM);
    }
}
