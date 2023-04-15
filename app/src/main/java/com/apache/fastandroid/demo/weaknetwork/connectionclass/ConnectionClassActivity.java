package com.apache.fastandroid.demo.weaknetwork.connectionclass;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apache.fastandroid.R;
import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;



public class ConnectionClassActivity extends Activity {

    private static final String TAG = "ConnectionClass-Sample";

    private ConnectionClassManager mConnectionClassManager;
    private DeviceBandwidthSampler mDeviceBandwidthSampler;
    private ConnectionChangedListener mListener;
    private TextView mTextView, mNetTextView;
    private View mRunningBar;

    private String mURL = "https://alifei05.cfp.cn/creative/vcg/800/version23/VCG41175510742.jpg";
    private String highUrl = "https://ww1.sinaimg.cn/mw690/006byKengy1hce95hnslxj60j60pk0x402.jpg";
    private String medUrl = "https://wx3.sinaimg.cn/mw690/004gSBbmgy1hcdix2vnxuj60u017tq8o02.jpg";
    private String lowUrl = "https://wx2.sinaimg.cn/mw690/005UMdTVly1hcfgd1r0jkj30to10sae0.jpg";
    private String tinyUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F2e8f7275-c6ac-45e6-a1ed-f0ee1b29582f%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1682871564&t=4fa379c09d63491889e8ffe35903a76c";
/*  private String mURL = "http://connectionclass.parseapp.com/m100_hubble_4060.jpg";
    private String highUrl = "http://wallpaperswide.com/download/star_wars_battlefront_stormtrooper-wallpaper-960x640.jpg";
    private String medUrl = "http://wallpaperswide.com/download/star_wars_battlefront_stormtrooper-wallpaper-480x320.jpg";
    private String lowUrl = "http://wallpaperswide.com/download/star_wars_battlefront_stormtrooper-wallpaper-320x480.jpg";
    private String tinyUrl = "http://wallpaperswide.com/download/star_wars_battlefront_stormtrooper-wallpaper-220x176.jpg";*/

    private int mTries = 0;
    private ConnectionQuality mConnectionClass = ConnectionQuality.UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_class);
        mConnectionClassManager = ConnectionClassManager.getInstance();
        mDeviceBandwidthSampler = DeviceBandwidthSampler.getInstance();
        findViewById(R.id.test_btn).setOnClickListener(testButtonClicked);
        mTextView = (TextView) findViewById(R.id.connection_class);
        mNetTextView = (TextView) findViewById(R.id.initial_class);
        mTextView.setText(mConnectionClassManager.getCurrentBandwidthQuality().toString());
        findCurrentNetSpeed();
        checkInitialQuality();
        mRunningBar = findViewById(R.id.runningBar);
        mRunningBar.setVisibility(View.GONE);
        mListener = new ConnectionChangedListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mConnectionClassManager.remove(mListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mConnectionClassManager.register(mListener);
    }

    private void checkInitialQuality() {
        String connectionQuality = "UNKNOWN";
        switch (NetApp.getSpeed()) {
            case 1:
                mURL = lowUrl;
                connectionQuality = "MODERATE";
                break;
            case 2:
                mURL = medUrl;
                connectionQuality = "GOOD";
                break;
            case 3:
                mURL = highUrl;
                connectionQuality = "EXCELLENT";
                break;
            default:
                mURL = tinyUrl;
                connectionQuality = "POOR";
                break;
        }
        // Every Time we check this, lets update the UI.
        mNetTextView.setText(connectionQuality);
    }

    /**
     * Listener to update the UI upon connectionclass change.
     */
    private class ConnectionChangedListener
            implements ConnectionClassManager.ConnectionClassStateChangeListener {

        @Override
        public void onBandwidthStateChange(ConnectionQuality bandwidthState) {
            mConnectionClass = bandwidthState;
            int setConType = 0;
            switch (bandwidthState.toString()) {
                case "POOR":
                    setConType = 0;
                    break;
                case "MODERATE":
                    setConType = 1;
                    break;
                case "GOOD":
                    setConType = 2;
                    break;
                case "EXCELLENT":
                    setConType = 3;
                    break;
            }
            NetApp.setSpeed(setConType);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTextView.setText(mConnectionClass.toString());
                }
            });
        }
    }

    private final View.OnClickListener testButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkInitialQuality();
            new DownloadImage().execute(mURL);
        }
    };

    /**
     * AsyncTask for handling downloading and making calls to the timer.
     */
    private class DownloadImage extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDeviceBandwidthSampler.startSampling();
            mRunningBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... url) {
            String imageURL = url[0];
            try {
                // Open a stream to download the image from our URL.
                URLConnection connection = new URL(imageURL).openConnection();
                connection.setConnectTimeout(3000);
                connection.setUseCaches(false);
                connection.connect();
                InputStream input = connection.getInputStream();
                try {
                    byte[] buffer = new byte[1024];

                    // Do some busy waiting while the stream is open.
                    while (input.read(buffer) != -1) {
                    }
                } finally {
                    input.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Error while downloading image.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            mDeviceBandwidthSampler.stopSampling();
            // Retry for up to 10 times until we find a ConnectionClass.
            if (mConnectionClass == ConnectionQuality.UNKNOWN && mTries < 3) {
                mTries++;
                new DownloadImage().execute(mURL);
            }
            if (!mDeviceBandwidthSampler.isSampling()) {
                mRunningBar.setVisibility(View.GONE);
            }
        }
    }

    public int findCurrentNetSpeed() {
        int networkSpeedClass = 1;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        int networkType = networkInfo.getType();
        boolean isWiFi = networkType == ConnectivityManager.TYPE_WIFI;
        boolean isMobile = networkType == ConnectivityManager.TYPE_MOBILE;
        boolean isConnected = networkInfo.isConnected();
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (!isConnected) {
            networkSpeedClass = 0;
        }

        // Go full speed when on Wifi
        if (isConnected && isWiFi) {
            networkSpeedClass = 3;
        }

        if (isConnected && isMobile) {
            String typeString = "Unknown";

            networkType = mTelephonyManager.getNetworkType();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    networkSpeedClass = 1;
                    break;
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    networkSpeedClass = 1;
                    break;
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    networkSpeedClass = 1;
                    break;
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    networkSpeedClass = 1;
                    break;
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    networkSpeedClass = 2;
                    break;
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    networkSpeedClass = 2;
                    break;
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    networkSpeedClass = 2;
                    break;
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    networkSpeedClass = 2;
                    break;
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    networkSpeedClass = 2;
                    break;
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                    networkSpeedClass = 3;
                    break;
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    networkSpeedClass = 3;
                    break;
                case TelephonyManager.NETWORK_TYPE_LTE:
                    networkSpeedClass = 3;
                    break;
                default:
                    networkSpeedClass = 2;
                    break;
            }
        }
        NetApp.setSpeed(networkSpeedClass);
        return networkSpeedClass;
    }
}