package com.tesla.framework.compat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by 01370340 on 2018/3/18.
 * Android 7.0适配
 */

public class NougatCompat {


    /**
     *
     * @param context
     * @param imgPath
     * @param requestCode
     */
    public static void openCamera(Activity context,String imgPath, int requestCode) {
        // 指定调用相机拍照后照片的储存路径
        File imgFile = new File(imgPath);
        Uri imgUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            //如果是7.0或以上
            imgUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", imgFile);
        } else {
            imgUri = Uri.fromFile(imgFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        context.startActivityForResult(intent, requestCode);
    }
}
