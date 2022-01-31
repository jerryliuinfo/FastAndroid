package com.apache.fastandroid.demo.kt.genericity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by Jerry on 2022/1/30.
 */
public class JavaGeneric {

    public static  <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException{
        return new Gson().fromJson(json, classOfT);
    }
}
