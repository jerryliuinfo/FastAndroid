package com.apache.fastandroid.demo.weaknetwork;

import android.util.Log;

import com.tesla.framework.component.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jerry on 2023/3/31.
 */
public class OkHttpNetworkTest implements INetworkTest {
    public static final String TAG = OkHttpNetworkTest.class.getSimpleName();
    long startTime;
    long endTime;
    long fileSize;
    OkHttpClient client = new OkHttpClient();

    // bandwidth in kbps
    private int POOR_BANDWIDTH = 150;
    private int AVERAGE_BANDWIDTH = 550;
    private int GOOD_BANDWIDTH = 2000;

    @Override
    public boolean testNetwork() {
        Request request = new Request.Builder()
                .url("https://alifei05.cfp.cn/creative/vcg/800/version23/VCG41175510742.jpg")
                .build();

        startTime = System.currentTimeMillis();

        boolean result = false;
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.d(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                InputStream input = response.body().byteStream();

                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];

                    while (input.read(buffer) != -1) {
                        bos.write(buffer);
                    }
                    byte[] docBuffer = bos.toByteArray();
                    fileSize = bos.size();

                } finally {
                    input.close();
                }

                endTime = System.currentTimeMillis();


                // calculate how long it took by subtracting endtime from starttime

                double timeTakenMills = Math.floor(endTime - startTime);  // time taken in milliseconds
                double timeTakenSecs = timeTakenMills / 1000;  // divide by 1000 to get time in seconds
                final int kilobytePerSec = (int) Math.round(1024 / timeTakenSecs);


                // get the download speed by dividing the file size by time taken to download
                double speed = fileSize / timeTakenMills;


                Logger.d("Time taken in secs: %s, kilobyte per sec:%s, Download Speed: %s, File size:%s",
                        timeTakenMills, kilobytePerSec, speed, fileSize
                );


                if (kilobytePerSec >= POOR_BANDWIDTH) {
                    // fast connection
                    result = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.d("OkHttpNetworkTest result:%s", result);
        return result;
    }
}
