/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-28 04:45:19
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.apache.fastandroid.artemis.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.text.TextUtils;

import com.tesla.framework.common.util.ResUtil;

import caom.apache.fastandroid.artemis.R;

public class IntentUtil {

    /**
     * 打开外部actvitiy
     * @param context
     * @param intent
     */
    public static void startActivity(Context context, Intent intent){
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 打开链接
     * 根据设置判断是用那种方式打开
     *
     * @param context 上下文
     * @param url     url
     */
    public static void openUrl(Context context, String url) {

        // TODO Chrome Custom Tabs
        if (TextUtils.isEmpty(url)) {
            return;
        }


        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        // 修改 ActionBar 的颜色
        intentBuilder.setToolbarColor(ResUtil.getColor(R.color.comm_white));
        //是否显示网页标题
        intentBuilder.setShowTitle(true);
        //自定义关闭 Custom tabs 的图标
        intentBuilder.setCloseButtonIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_arrow_back_black_24dp));
        // 最后调用助手类 CustomTabActivityHelper 的 openCustomTab 函数来打开一个网址

        intentBuilder.build().launchUrl(context, Uri.parse(url));
    }



    /**
     * 打开支付宝
     */
    public static void openAlipay(Context context) {
        /*if (AppUtil.isAvailable(context, "com.eg.android.AlipayGphone")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String QRCode = "HTTPS://QR.ALIPAY.COM/FKX07101FYSJGTNCAPQW39";
            intent.setData(Uri.parse("alipayqr://platformapi/startapp?saId=10000007&qrcode=" + QRCode));
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "你没有捐赠的权限", Toast.LENGTH_SHORT).show();
        }*/
    }
}
