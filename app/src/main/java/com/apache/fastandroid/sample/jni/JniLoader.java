package com.apache.fastandroid.sample.jni;



public class JniLoader {


    private static volatile boolean mIsLoaded = false;

    static {
        loadLibrary();
    }

    /**
     * 统一加载对应的so
     * @return
     */
    public static boolean loadLibrary() {
        if (!mIsLoaded) {
            try {
                System.loadLibrary("libName");
                // 检查一下so是否加载正常，对应的native方法有没有
                mIsLoaded = true;
            } catch (UnsatisfiedLinkError e) {
            } catch (Exception e) {
            }
            if (!mIsLoaded) {
                // 上报加载失败
            }
        }
        return mIsLoaded;
    }
}
