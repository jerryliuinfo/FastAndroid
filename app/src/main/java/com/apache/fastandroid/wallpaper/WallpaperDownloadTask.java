package com.apache.fastandroid.wallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.tesla.framework.common.util.dimen.ScreenUtil;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.network.task.TaskException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by 01370340 on 2017/11/21.
 */

public class WallpaperDownloadTask  {
    public static final String TAG = WallpaperDownloadTask.class.getSimpleName();
    // 设置壁纸
    public static void setWallpaper(Context context, File file, final OnProgressCallback callback) throws TaskException {
        long time = System.currentTimeMillis();
        try {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);

            try {
                DisplayMetrics dm = new DisplayMetrics();
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                windowManager.getDefaultDisplay().getMetrics(dm);
                int width = dm.widthPixels;
                int height = dm.heightPixels;

                int navigationBarHeight = ScreenUtil.getNavigationBarHeight(context);

                int wallpaperHeight = height;


                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                //这里主要是获取图片的尺寸
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);

                boolean decode = false;

                if (opts.outHeight > height + navigationBarHeight) {
                    decode = true;
                }

                FastLog.d(TAG, "image height = %d, screen height = %d", opts.outHeight, height + navigationBarHeight);

                if (decode) {
                    // docode的时间会稍微长一点，idol3测试在1S内，所以先显示一个99%的进度
//                    if (progress <= 0) {
//                        progress = 999l;
//                        total = 1000l;
//                        publishProgress(progress, total);
//                    }

                    opts.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

                    Matrix matrix = new Matrix();
                    float scale = wallpaperHeight * 1.0f / bitmap.getHeight();
                    matrix.setScale(scale, scale);

                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
                    wallpaperManager.setStream(in);

                    if (callback != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {

                            @Override
                            public void run() {
                                callback.onSetWallpaper(true);
                            }

                        });
                    }

                    FastLog.d(TAG, "设置处理后的壁纸耗时 : " + (System.currentTimeMillis() - time));

                    return;
                }
            } catch (Throwable e) {
                e.printStackTrace();
                //NLog.printExc(WallpaperDownloadTask.class, e);
            }

            wallpaperManager.setStream(new FileInputStream(file));

            FastLog.d(TAG, "设置原壁纸耗时 : " + (System.currentTimeMillis() - time));

            if (callback != null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        callback.onSetWallpaper(true);
                    }

                });
            }
        } catch (Exception e) {

            if (callback != null) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        callback.onSetWallpaper(false);
                    }

                });
            }

            throw new TaskException("", "设置壁纸失败 ");
        }
    }



    public interface OnProgressCallback {

        // 回调下载进度
        void onProgressUpdate(String image, long progress, long total, int flag);

        // 设置壁纸成功
        void onSetWallpaper(boolean success);

        // 回调弹框消息
        void showMessage(String image, String text);

        // 取消下载
        void onCanceled(String image);

        // 未下载，初始化视图
        void setInit(String image);

    }
}
