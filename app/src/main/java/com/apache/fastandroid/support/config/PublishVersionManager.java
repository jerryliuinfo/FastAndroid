package com.apache.fastandroid.support.config;

import android.content.Context;
import android.content.pm.PackageInfo;

import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.util.XmlUtil;

/**
 * Created by jerryliu on 2017/6/3.
 */

public class PublishVersionManager {



    // 渠道相关配置信息 sChannel是渠道名称
    private static String sChannel;
    //渠道id 每个渠道名称都有一个渠道id,跟服务器保持一致
    private static String sChannelId;

    private static final String CHANNEL_TEST = "buildVersion"; // 测试版本，一般是给测试使用
    private static final String CHANNEL_GP = "gp"; //gp渠道
    private static final String CHANNEL_BAIDU = "baidu";//百度渠道
    private static final String CHANNEL_HAWK = "hawk"; //企业内部渠道

    private static final String CHANNEL_TEST_ID = "1000";//测试渠道ID
    private static final String CHANNEL_GP_ID = "2000";//GP渠道ID
    private static final String CHANNEL_BAIDU_ID = "3000";//百度渠道ID
    private static final String CHANNEL_HAWK_ID = "4000";//企业内部渠道ID

    private static boolean sIsHawkInnerChannel; //是否企业内部渠道

    //应用版本名称和版本号
    private static String sVersionName;
    private static int sVersionCode;



    //配置是否是测试环境
    private static boolean sIsTest;
    private static final String DOMAIN_TEST_VALUE = "${ISTEST_VALUE}";
    private static final String DOMAIN_TEST = "TEST";

    static {
        initIsTest(FrameworkApplication.getContext());
        initChannelInfo(FrameworkApplication.getContext());
        initVersionInfo(FrameworkApplication.getContext());
    }


    private static void initVersionInfo(Context context) {
        try {
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            sVersionName = pkgInfo.versionName;
            sVersionCode = pkgInfo.versionCode;
        } catch (Exception e) {
            sVersionName = "";
            sVersionCode = 0;
        }
    }
    /**
     * 初始化渠道信息
     * @param context
     */
    private static void initChannel(Context context) {
        String channel = XmlUtil.getMetaData(context, "CHANNEL");
        sChannel = channel == null ? "" : channel;
    }

    private static void initChannelId() {
        String channelId;
        switch (sChannel) {
            case CHANNEL_TEST:
                channelId = CHANNEL_TEST_ID;
                break;
            case CHANNEL_GP:
                channelId = CHANNEL_GP_ID;
                break;
            case CHANNEL_BAIDU:
                channelId = CHANNEL_BAIDU_ID;
                break;
            case CHANNEL_HAWK:
                channelId = CHANNEL_HAWK_ID;
                break;
            default:
                channelId = "";
        }
        sChannelId = channelId;
    }


    private static void initChannelInfo(Context context){
        initChannel(context);
        initChannelId();
    }


    /**
     * 非PRD则认为是测试环境
     * @param context
     */
    private static void initIsTest(Context context) {
        String isTest = XmlUtil.getMetaData(context, "ISTEST");
        sIsTest = DOMAIN_TEST.equalsIgnoreCase(isTest) || DOMAIN_TEST_VALUE.equalsIgnoreCase(isTest);
    }

    public static void isHawkInnerChannel() {
        sIsHawkInnerChannel = CHANNEL_HAWK.equalsIgnoreCase(sChannel);
    }




    /**
     * @return 是否是测试版本
     */
    public static boolean isTest() {
        return sIsTest;
    }

    /**
     * 尽量不要使用该方法，确实需要，请联系chaohao.zhou新增接口
     * @return 返回Channel名字
     */
    public static String getChannel() {
        return sChannel;
    }

    /**
     * @return 渠道Id，跟服务器保持一致
     */
    public static String getChannelId() {
        return sChannelId;
    }

}
