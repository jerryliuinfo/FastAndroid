package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.bean.VersionResponseBean
import com.apache.fastandroid.databinding.GsonDemoBinding
import com.chad.baserecyclerviewadapterhelper.entity.Person
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.gson_demo.*

/**
 * Created by Jerry on 2021/3/1.
 */
class GsonFragment: BaseVBFragment<GsonDemoBinding>(GsonDemoBinding::inflate) {
    companion object{
        val TAG = "GsonFragment"
    }


    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn1.setOnClickListener {
            try {
                val jso1 = " {\"code\":0,\"msg\":\"Success\",\"data\":\"\"}"
                val result1 = Gson().fromJson(jso1,
                        VersionResponseBean::class.java)
                NLog.d(TAG, "result1: %s", result1)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btn2.setOnClickListener {
            try {
                val json2 = "  {\"code\":0,\"msg\":\"Success\",\"data\":{}}"
                val result2 = Gson().fromJson(json2,
                        VersionResponseBean::class.java)
                NLog.d(TAG, "result2: %s", result2)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        btn3.setOnClickListener {
            var builder = GsonBuilder();
            /*如果不设置serializeNulls,序列化时默认忽略NULL*/
            builder.serializeNulls();   // 1
            /*使打印的json字符串更美观，如果不设置，打印出来的字符串不分行*/
            builder.setPrettyPrinting();
            val gson = builder.create()
            var person = Person("Jerry",10)

            val json = gson!!.toJson(person);
            NLog.d(TAG, "btn3 json: %s",json);

            var person2 = gson.fromJson(json, Person::class.java)
            NLog.d(TAG, "btn3 person2: %s",json);


        }

        btn4.setOnClickListener {
            var builder = GsonBuilder();
            /*如果不设置serializeNulls,序列化时默认忽略NULL*/
//      builder.serializeNulls();   // 1
            /*使打印的json字符串更美观，如果不设置，打印出来的字符串不分行*/
            builder.setPrettyPrinting();
            val gson = builder.create()
            var person = Person("Jerry",11)

            val json = gson!!.toJson(person);
            NLog.d(TAG, "btn4 json: %s",json);

            var person2 = gson.fromJson(json, Person::class.java)
            NLog.d(TAG, "btn4 person2: %s",json);


        }

    }




}