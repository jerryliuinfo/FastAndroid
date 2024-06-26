package com.apache.fastandroid.demo.hitpit

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCommonBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/12/14.
 * 参考：https://mp.weixin.qq.com/s/81vD1sT9F5LR07H9fqj5QQ
 */
class DataClassPitDemoFragment:BaseBindingFragment<FragmentCommonBinding>(FragmentCommonBinding::inflate) {

    companion object{
        private const val TAG = "DataClassPitDemoFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val sample = SampleBean(0)
        val map = mutableMapOf<SampleBean,String>()
        map[sample] = "1"
        //可以拿到 value:!

        sample.id = 2

        //拿不到 value 了

        //但对象还是同一个
    }
}