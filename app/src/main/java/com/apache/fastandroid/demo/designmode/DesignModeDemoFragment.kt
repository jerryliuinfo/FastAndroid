package com.apache.fastandroid.demo.designmode

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.constraint.practice.ConstraintBasicFragment
import com.apache.fastandroid.demo.designmode.paramtype.ParamTypeDemoFragment

/**
 * Created by Jerry on 2021/3/1.
 */
class DesignModeDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("观察者模式","观察者模式",ObserverModeFragment::class.java)
            ,ViewItemBean("LifeCycleOwner","防系统ComponentActivity监听声明周期",ObserverModeFragment::class.java)
            ,ViewItemBean("代理模式","代理模式",ProxyDemoFragment::class.java)
            ,ViewItemBean("GetService","根据传入的参数类型决定返回参数类型对象",ParamTypeDemoFragment::class.java)

        )
    }
}