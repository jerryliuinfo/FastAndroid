package com.apache.fastandroid.demo.hitpit

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCommonBinding
import com.apache.fastandroid.demo.bean.Person
import com.blankj.utilcode.util.GsonUtils
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/12/14.
 * 参考：https://mp.weixin.qq.com/s/jVRTFTiwTtr7P7vyAj8G7A
 */
class GsonSerializeDemoFragment:BaseVBFragment<FragmentCommonBinding>(FragmentCommonBinding::inflate) {

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