package com.apache.fastandroid.artemis.retrofit.download;

import android.text.TextUtils;

import com.apache.fastandroid.artemis.BaseApp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by 01370340 on 2018/4/14.
 */

public class Downloader {

    public File downloadFile(ResponseBody response,String destFileDir, final String destFileName, ProgressListener progressListener) throws Exception{
        if (TextUtils.isEmpty(destFileDir)){
            destFileDir = BaseApp.getExternalFilePath() + File.separator;
        }
        long contentLength = response.contentLength();
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        try {
            is = response.byteStream();

            long sum = 0;

            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);

                final long finalSum = sum;

                progressListener.onResponseProgress(finalSum, contentLength, (int) ((finalSum * 1.0f / contentLength) * 100), finalSum == contentLength, file.getAbsolutePath());
            }
            fos.flush();

            return file;

        } finally {
            try {
                response.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }

        }
    }
}
