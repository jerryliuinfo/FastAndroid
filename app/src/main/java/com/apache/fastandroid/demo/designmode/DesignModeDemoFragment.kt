package com.apache.fastandroid.demo.designmode

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.constraint.practice.ConstraintBasicFragment

/**
 * Created by Jerry on 2021/3/1.
 */
class DesignModeDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("观察者模式","观察者模式",ObserverModeFragment::class.java)
            ,ViewItemBean("LifeCycleOwner","防系统ComponentActivity监听声明周期",ObserverModeFragment::class.java)

        )
    }
}