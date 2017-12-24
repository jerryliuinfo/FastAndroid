package com.tesla.framework.common.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 专用于分享的工具类
 *
 */

public class ShareUtil {
    public static void share(Context context, int stringRes) {
        share(context, context.getString(stringRes));
    }

    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "分享"));
    }
}
