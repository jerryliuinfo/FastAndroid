package com.apache.fastandroid.sample.singleton;




public class PropsConfig {

    private static final String TAG = "PropsConfig";

    private static Singleton<PropsConfig, Void> sInstance = new Singleton<PropsConfig, Void>() {
        @Override
        protected PropsConfig create(Void aVoid) {
            return new PropsConfig();
        }
    };

    public static PropsConfig get() {
        return sInstance.get(null);
    }



}
