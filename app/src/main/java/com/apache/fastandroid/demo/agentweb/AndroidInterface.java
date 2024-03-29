package com.apache.fastandroid.demo.agentweb;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;

/**
 * Created by cenxiaozhong on 2017/5/14.
 *  source code  https://github.com/Justson/AgentWeb
 */

public class AndroidInterface {
    public static final String TAG = "AndroidInterface";

    private Handler deliver = new Handler(Looper.getMainLooper());
    private AgentWeb agent;
    private Context context;

    public AndroidInterface(AgentWeb agent, Context context) {
        this.agent = agent;
        this.context = context;
    }



    @JavascriptInterface
    public void callAndroid(final String msg) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
            }
        });
        Log.i("Info", "Thread:" + Thread.currentThread());

    }

}
