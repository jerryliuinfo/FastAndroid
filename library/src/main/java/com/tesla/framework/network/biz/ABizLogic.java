package com.tesla.framework.network.biz;

import android.text.TextUtils;

import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.common.setting.SettingExtra;
import com.tesla.framework.common.setting.SettingUtility;
import com.tesla.framework.common.util.Logger;
import com.tesla.framework.network.cache.CacheExecutor;
import com.tesla.framework.network.cache.ICacheUtility;
import com.tesla.framework.network.http.DefHttpUtility;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.IHttpUtility;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;

/**
 * Created by jerryliu on 2017/4/8.
 */

public abstract class ABizLogic implements IHttpUtility{
    public static final String TAG = "BizLogic";

    public static String getTag(Setting action, String append) {
        return TAG + "-" + action.getType() + "-" + append;
    }

    // 服务地址
    public static final String BASE_URL = "base_url";
    // 缓存类型
    public static final String CACHE_UTILITY = "cache_utility";
    // 网络协议
    public static final String HTTP_UTILITY = "http";


    public enum CacheMode {
        /**
         * 有缓存且有效，返回缓存<br/>
         * 有缓存但无效，拉取服务数据，如果拉取失败，仍然返回无效缓存<br/>
         * 没有缓存，拉取服务数据
         */
        auto,
        /**
         * 在{@link #auto}的基础上，不管存不存在缓存，都拉取服务数据更新缓存
         */
        servicePriority,
        /**
         * 每次拉取数据，都优先拉取缓存
         */
        cachePriority,
        /**
         * 只拉取服务数据
         */
        disable
    }

    private CacheMode mCacheMode;

    public ABizLogic() {
        mCacheMode = CacheMode.disable;
    }

    public ABizLogic(CacheMode cacheMode) {
        this.mCacheMode = cacheMode;
    }




    @Override
    public <T> T doGet(HttpConfig config, Setting action, Params params, Class<T> responseCls) throws TaskException {
        ICacheUtility cacheUtility = null;
        T cache = null;
        if (action.getExtras().containsKey(CACHE_UTILITY)){
            if (!TextUtils.isEmpty(action.getExtras().get(CACHE_UTILITY).getValue())){
                try {
                    cacheUtility = (ICacheUtility) Class.forName(action.getExtras().get(CACHE_UTILITY).getValue()).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.e(TAG, "CacheUtility配置错误 ");
                }
            }
        }else {
            Logger.e(TAG, "没有配置CacheUtility");
        }
        Logger.e(TAG, "mCacheMode = %s", mCacheMode);
        //没有禁止缓存 则先读取缓存
        if (mCacheMode != CacheMode.disable){
            if (cacheUtility != null){
                long startTime = System.currentTimeMillis();
                cache = (T) cacheUtility.findCacheData(action,params);
                if (cache != null){
                    Logger.d(TAG, "读取缓存耗时 %s ms", System.currentTimeMillis() - startTime);
                }
            }
        }

        //缓存不存在、服务数据优先等情况下，拉取服务数据
        if (cache == null || mCacheMode == CacheMode.servicePriority){
            try {
                long time = System.currentTimeMillis();
                T result = getHttpUtility(action).doGet(resetHttpConfig(config, action), action, params, responseCls);
                Logger.d(getTag(action, "Get-Http"), "耗时 %s ms", String.valueOf(System.currentTimeMillis() - time));

                if (result != null && result instanceof IResult) {
                    putToCache(action, params, (IResult) result, cacheUtility);
                }

                Logger.d(getTag(action, "Get-Http"), "返回服务器数据");
                return result;
            } catch (TaskException e) {
                Logger.w(getTag(action, "Exception"), e + "");
                throw e;
            } catch (Exception e) {
                Logger.w(getTag(action, "Exception"), e + "");
                throw new TaskException(TextUtils.isEmpty(e.getMessage()) ? "服务器错误" : e.getMessage());
            }

        }else {
            if (cache != null) {
                Logger.d(getTag(action, "Cache"), "返回缓存数据");
                // 缓存存在，且有效
                return cache;
            }
        }

        return null;
    }

    @Override
    public <T> T doPost(HttpConfig config, Setting action, Params urlParams, Params bodyParams, Object requestObj,
                        Class<T> responseCls) throws TaskException {
        return null;
    }

    @Override
    public <T> T doPostFiles(HttpConfig config, Setting action, Params urlParams, Params bodyParams, MultipartFile[] files, Class<T> responseCls) throws TaskException {
        return null;
    }


    private IHttpUtility getHttpUtility(Setting action){
        if (action.getExtras().containsKey(HTTP_UTILITY) && !TextUtils.isEmpty(action.getExtras().get(HTTP_UTILITY).getValue())){

            try {
                IHttpUtility httpUtility = (IHttpUtility) Class.forName(action.getExtras().get(HTTP_UTILITY).getValue()).newInstance();
                return httpUtility;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return configHttpUtility();

    }

    /**
     * 配置网络交互
     *
     * @return
     */
    protected IHttpUtility configHttpUtility() {
        // 取配置的http
        try {
            if(!TextUtils.isEmpty(SettingUtility.getStringSetting("http")))
                return (IHttpUtility) Class.forName(SettingUtility.getStringSetting("http")).newInstance();
        } catch (Exception e) {
            Logger.printExc(ABizLogic.class, e);
        }

        return new DefHttpUtility();
    }


    private HttpConfig resetHttpConfig(HttpConfig config, Setting actionSetting) {
        try {
            // 重写BaseURL
            if (actionSetting != null && actionSetting.getExtras().containsKey(BASE_URL))
                config.baseUrl = actionSetting.getExtras().get(BASE_URL).getValue().toString();
        } catch (Exception e) {
        }

        return config;
    }

    protected HttpConfig getHttpConfig() {
        return configHttpConfig();
    }


    /**
     * 设置http相关参数
     *
     * @return
     */
    abstract protected HttpConfig configHttpConfig();


    public void putToCache(Setting setting, Params params, IResult data, ICacheUtility cacheUtility) {
        if (data != null && cacheUtility != null && !data.isFromCache()) {
            new PutCacheTask(setting, params, data, cacheUtility).executeOnExecutor(CacheExecutor.getCacheExecutor());
        }
    }

    class PutCacheTask extends WorkTask<Void, Void, Void> {

        private Setting setting;
        private Params params;
        private IResult o;
        private ICacheUtility cacheUtility;

        PutCacheTask(Setting setting, Params params, IResult o, ICacheUtility cacheUtility) {
            this.setting = setting;
            this.params = params;
            this.o = o;
            this.cacheUtility = cacheUtility;
        }

        @Override
        public Void workInBackground(Void... p) throws TaskException {
            long time = System.currentTimeMillis();

            Logger.d(getTag(setting, "Cache"), "开始保存缓存");

            cacheUtility.addCache(setting, params, o);

            Logger.d(getTag(setting, "Cache"), "保存缓存耗时 %s ms", String.valueOf(System.currentTimeMillis() - time));

            return null;
        }

    }

    public static Setting getSetting(String type) {
        return SettingUtility.getSetting(type);
    }

    protected Setting newSetting(String type, String value, String desc) {
        Setting extra = new Setting();

        extra.setType(type);
        extra.setValue(value);
        extra.setDescription(desc);

        return extra;
    }

    protected SettingExtra newSettingExtra(String type, String value, String desc) {
        SettingExtra extra = new SettingExtra();

        extra.setType(type);
        extra.setValue(value);
        extra.setDescription(desc);

        return extra;
    }




}
