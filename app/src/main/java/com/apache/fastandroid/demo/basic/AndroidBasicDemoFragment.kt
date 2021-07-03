package com.apache.fastandroid.demo.basic

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.constraint.practice.ConstraintBasicFragment
import java.util.*

/**
 * Created by Jerry on 2021/3/1.
 */
class AndroidBasicDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("启动模式", "启动模式", LaunchModeFragment::class.java)
                ,ViewItemBean("RecycleView ItemDecoration", "ItemDecoration", RecycleViewItemDecorationFragment::class.java)

        )
    }
}