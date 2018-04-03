package com.tesla.framework.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author yaodingding
 * @Description: 打开第三方app 工具类
 * @date 2016/8/9
 * @copyright TCL-MIG
 */
public class OpenThirdPartyActivityUtils {
    private static final String TAG = OpenThirdPartyActivityUtils.class.getSimpleName();



    /**
     * 打开邮箱发送邮件
     */
    public static void openEmail(Context context, String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + email));//email 暂时写在了这里
        startThirdPartyActivity(context, intent);
    }

    /**
     * 通过第三方浏览器打开url
     *
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
            boolean result = startThirdPartyActivity(context, intent);
            if (!result) { //没有浏览器
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 打第三方app 暂时写在了这里。
     *
     * @param intent
     * @return
     */
    public static boolean startThirdPartyActivity(Context context, Intent intent) {
        boolean result = false;
        if (intent == null) {
            return result;
        }
        if (intent.resolveActivity(context.getPackageManager()) != null) {//存第三方Activity
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            result = true;
        }
        return result;
    }
}
