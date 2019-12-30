package com.tesla.framework.component.okhttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jerry on 2019-12-29.
 */
public class JsonHttpRequest implements IHttpRequest {

    CallbackListener listener;

    @Override
    public void setUrl(String url) {

    }

    @Override
    public void setData(byte[] data) {

    }


    @Override
    public void setListener(CallbackListener listener) {
        this.listener = listener;
    }

    @Override
    public void execute() {
        HttpURLConnection connection =  null;
        BufferedReader reader = null;
        try {
            URL url = new URL("http://www.baidu.com");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            //此时获取的是字节流
            InputStream in = connection.getInputStream();
            //对获取到的输入流进行读取
            reader = new BufferedReader(new InputStreamReader(in)); //将字节流转化成字符流
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine())!= null) {
                response.append(line);
            }
            //showResponse(response.toString());
            listener.onSuccess(in);


        } catch (Exception e ) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e ) {
                    e.printStackTrace();
                }
            }
            if ( connection!= null) {
                connection.disconnect();
            }
        }


    }

}
