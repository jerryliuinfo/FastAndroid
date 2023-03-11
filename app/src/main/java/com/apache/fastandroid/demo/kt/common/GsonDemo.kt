package com.apache.fastandroid.demo.kt.common

import com.apache.fastandroid.bean.Person
import com.apache.fastandroid.bean.VersionResponseBean
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/4/30.
 */
class GsonDemo {
    companion object{
        private const val TAG = "GsonDemo"
    }

    /**
     *
     * data为空字符串序列化
     *
     */
    fun jsonDataIsNull(){
        try {
            val jso1 = " {\"code\":0,\"msg\":\"Success\",\"data\":\"\"}"
            val result1 = Gson().fromJson(jso1,
                VersionResponseBean::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * data为空json对象序列化
     */
    fun jsonDataContentIsNull(){
        try {
            val json2 = "  {\"code\":0,\"msg\":\"Success\",\"data\":{}}"
            val result2 = Gson().fromJson(json2,
                VersionResponseBean::class.java)
        } catch (e: Exception) {
            e.printStackTrace()

        }

    }

    fun serializeNull(){
        var builder = GsonBuilder();
        /*如果不设置serializeNulls,序列化时默认忽略NULL*/
        builder.serializeNulls();   // 1
        /*使打印的json字符串更美观，如果不设置，打印出来的字符串不分行*/
        builder.setPrettyPrinting();
        val gson = builder.create()
        var person = Person("Jerry",10)

        val json = gson!!.toJson(person);

        var person2 = gson.fromJson(json, Person::class.java)
        Logger.d("btn3 person2: %s",json)

    }

    fun serializeNotNull(){
        var builder = GsonBuilder();
        /*如果不设置serializeNulls,序列化时默认忽略NULL*/
//      builder.serializeNulls();   // 1
        /*使打印的json字符串更美观，如果不设置，打印出来的字符串不分行*/
        builder.setPrettyPrinting();
        val gson = builder.create()
        var person = Person("Jerry",11)

        val json = gson.toJson(person);
        Logger.d( "btn4 json: %s",json);

        var person2 = gson.fromJson(json, Person::class.java)
        Logger.d( "btn4 person2: %s",json)


    }
}