package com.apache.fastandroid.demo.hitpit

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.UserBean
import com.apache.fastandroid.demo.bean.People
import com.apache.fastandroid.demo.bean.Person
import com.apache.fastandroid.state.UserInfo
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.Gson
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import java.lang.Exception
import java.lang.reflect.Type

/**
 * Created by Jerry on 2021/12/14.
 * 参考：https://mp.weixin.qq.com/s/jVRTFTiwTtr7P7vyAj8G7A
 */
class GsonSerializeDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_common
    }
    companion object{
        private const val TAG = "DataClassPitDemoFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        val person = Person("zhangsan", 18)
        //{"age":18,"name":"zhangsan"}
        NLog.d(TAG, GsonUtils.toJson(person))

        val json = "{\"age\":18}"

        //虽然没有传 name 值，但是反序列化不会报错，只是 name的值为 null
        val person2 = GsonUtils.fromJson(json,Person::class.java)
        NLog.d(TAG, "person2:${person2}")


    }





}