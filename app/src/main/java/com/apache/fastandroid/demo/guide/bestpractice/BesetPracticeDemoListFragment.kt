package com.apache.fastandroid.demo.guide.bestpractice

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.service.ServiceDemoFragment

/**
 * Created by Jerry on 2024/3/2.
 */
class BesetPracticeDemoListFragment : BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("测试最佳实践", "测试最佳实践", ServiceDemoFragment::class.java),

            )
    }
}