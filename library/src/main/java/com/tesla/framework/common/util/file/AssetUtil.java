package com.tesla.framework.common.util.file;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jerryliu on 2017/8/3.
 */

public class AssetUtil {
    public static final String TAG = AssetUtil.class.getSimpleName();

    public static String readAssetsFile(String fileName, Context context) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String readLine = null;
            while ((readLine = reader.readLine()) != null) {
                sb.append(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


    /**
     * 从assets目录中复制某文件内容
     *  @param  assetFileName assets目录下的Apk源文件路径
     *  @param  newFileName 复制到/data/data/package_name/files/目录下文件名
     */
    private void copyAssetsFileToAppFiles(Context context,String assetFileName, String newFileName) {
        InputStream is = null;
        FileOutputStream fos = null;
        int buffsize = 1024;

        try {
            is = context.getAssets().open(assetFileName);
            fos = context.openFileOutput(newFileName, Context.MODE_PRIVATE);
            int byteCount = 0;
            byte[] buffer = new byte[buffsize];
            while((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
