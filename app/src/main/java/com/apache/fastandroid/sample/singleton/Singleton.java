package com.apache.fastandroid.sample.singleton;


public abstract class Singleton<T, P> {
    /**
     * 唯一实例
     */
    private volatile T mInstance;

    /**
     * 创建实例
     *
     * @param p 创建实例所需指定的参数
     *          exp: Context context
     * @return 实例
     */
    protected abstract T create(P p);

    /**
     * 获取实例
     *
     * @param p 创建实例时指定的参数
     *          exp: Context context
     * @return 实例
     */
    public final T get(P p) {
        if (mInstance == null) {
            synchronized (this) {
                if (mInstance == null) {
                    mInstance = create(p);
                }
            }
        }
        return mInstance;
    }

    public final T get() {
        return get(null);
    }
}
