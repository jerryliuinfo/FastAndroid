/*
 * Copyright 2017 GcsSloop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 2017-03-08 01:01:18
 *
 * GitHub:  https://github.com/GcsSloop
 * Website: http://www.gcssloop.com
 * Weibo:   http://weibo.com/GcsSloop
 */

package com.apache.fastandroid.artemis;

import androidx.annotation.NonNull;

import com.apache.fastandroid.artemis.support.bean.Token;
import com.tesla.framework.common.util.sdcard.SdcardUtils;
import com.tesla.framework.support.cache.ACache;

import java.io.File;

/**
 * 缓存工具类，用于缓存各类数据
 */
public class CacheUtil {

    private static String CACHE_DIR = SdcardUtils.getSdcardPath()+ File.separator;
    private static ACache cache = ACache.get(new File(CACHE_DIR));


    //--- token ------------------------------------------------------------------------------------

    public static void saveToken(@NonNull Token token){
        cache.put("token", token);
    }

    public static Token getToken(){
        return (Token) cache.getAsObject("token");
    }

    public static void clearToken(){
        cache.remove("token");
    }

    public static boolean isLogin() {
        return getToken() != null;
    }
}
