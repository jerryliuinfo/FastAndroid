package com.apache.fastandroid;

import android.support.test.runner.AndroidJUnit4;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
    }


    @Test
    public void testMetaData() throws Exception {

    }


    @Test
    public void testOkHttp()throws Exception{
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.build();

        Request request = new Request.Builder().url("").build();
        Response response = okHttpClient.newCall(request).execute();
    }
}
