package com.apache.fastandroid.demo.hitpit

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/12/14.
 * 参考：https://mp.weixin.qq.com/s/81vD1sT9F5LR07H9fqj5QQ
 */
class DataClassPitDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_common
    }
    companion object{
        private const val TAG = "DataClassPitDemoFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val sample = SampleBean(0)
        val map = mutableMapOf<SampleBean,String>()
        map[sample] = "1"
        //可以拿到 value:!
        NLog.d(TAG,"before modify:"+ map[sample])

        sample.id = 2

        //拿不到 value 了
        NLog.d(TAG,"after modify:"+ map[sample])

        //但对象还是同一个
        NLog.d(TAG,"is the same obj:"+ (map.keys.first() == sample))
    }
}