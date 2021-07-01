package com.apache.fastandroid.retrofit.convertor;

/**
 * Created by Jerry on 2021/3/24.
 */

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.tesla.framework.common.util.log.NLog;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 功能描述：json解析相关
 */
public final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    public static final String TAG = GsonResponseBodyConverter.class.getSimpleName();
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            return adapter.read(jsonReader);
        } catch (Exception e){
            e.printStackTrace();
            NLog.e(TAG, e);
        }finally {
            value.close();
        }return null;
    }



}