package com.tesla.framework.common.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 866315 on 2015/10/10.
 */
public class GsonUtils {

    private GsonUtils(){
        //留空
    }

    static Gson mGson = new GsonBuilder().disableHtmlEscaping().create();

    public static <T> T json2Bean(String result, Class<T> clazz) {
        T t =mGson.fromJson(result, clazz);
        return t;
    }

    public static String bean2Json(Object obj) {
        return mGson.toJson(obj);
    }



    public static <T> List<T> json2Array(String result, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);
        if (element.isJsonObject()) {
            list.add(mGson.fromJson(element, clazz));
        } else if (element.isJsonArray()) {
            for (JsonElement obj : element.getAsJsonArray()) {
                T t = mGson.fromJson(obj, clazz);
                list.add(t);
            }
        }
        return list;
    }

    /**
     * demo: GsonUtils.json2Array(result.toString(), new TypeToken<List<ProdPriceBean>>(){})
     */
    public static <T> List<T> json2Array(String result, TypeToken<List<T>> typeToken) {
        return mGson.fromJson(result, typeToken.getType());
    }






    public static <T> String array2Json(List<T> lists) {
        Type listType = new TypeToken<List<T>>() {
        }.getType();
        return  mGson.toJson(lists, listType);
    }


    public static Map<String, String> json2Map(String result) {
        return mGson.fromJson(result, Map.class);
    }

    public static String map2Json(Map<String, Object> result) {
        return mGson.toJson(result);
    }
}
