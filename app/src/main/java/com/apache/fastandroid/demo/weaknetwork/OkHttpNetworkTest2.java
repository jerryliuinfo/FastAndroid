package com.apache.fastandroid.demo.weaknetwork;

import com.tesla.framework.component.logger.Logger;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jerry on 2023/3/31.
 */
public class OkHttpNetworkTest2 implements INetworkTest {
    public static final String TAG = OkHttpNetworkTest2.class.getSimpleName();


    @Override
    public boolean testNetwork() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://speedtest.tele2.net/1MB.zip")
                .build();

        final long startTime = System.currentTimeMillis();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                InputStream inputStream = response.body().byteStream();
                byte[] buffer = new byte[1024];
                int len;
                long bytesWritten = 0;
                while ((len = inputStream.read(buffer)) != -1) {
                    bytesWritten += len;
                }
                long endTime = System.currentTimeMillis();
                float speed = (bytesWritten / (endTime - startTime)) / 1024f;
                final String msg = "当前网速为：" + String.format("%.2f", speed) + "KB/s";
                Logger.d("OkHttpNetworkTest2 speed:%s", speed);

            }
        });
        return false;
    }
}
