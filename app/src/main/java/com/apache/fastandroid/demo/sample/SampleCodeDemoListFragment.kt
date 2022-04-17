package com.apache.fastandroid.demo.sample

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2021/12/12.
 * CustomImageView 的 onDraw 方法中调用 setImageResource 会触发 scheduleTravasel 导致主线程消息队列一直不为空，导致
 * queueIdle 一直不被执行
 * 参考:https://juejin.cn/post/6936440588635996173?share_token=e3cb2750-a5d9-498b-b345-1539fe421665
 *
 */
class SampleCodeDemoListFragment:BaseListFragment() {

    companion object{
        private val TAG = "HitPitDemoListFragment"
    }

    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("registerCallback","registerCallback",SampleCode1DemoFragment::class.java)

        )

    }



}