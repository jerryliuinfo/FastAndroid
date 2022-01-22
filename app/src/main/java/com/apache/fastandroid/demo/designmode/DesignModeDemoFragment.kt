package com.apache.fastandroid.demo.designmode

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.designmode.builder.BuilderModeDemoFragment
import com.apache.fastandroid.demo.designmode.chain.ChainModeDemoFragment
import com.apache.fastandroid.demo.designmode.paramtype.ParamTypeDemoFragment
import com.apache.fastandroid.demo.designmode.wrapper.WrapperDesignMode

/**
 * Created by Jerry on 2021/3/1.
 */
class DesignModeDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("观察者模式","观察者模式",ObserverModeFragment::class.java)
            ,ViewItemBean("LifeCycleOwner","防系统ComponentActivity监听声明周期",ObserverModeFragment::class.java)
            ,ViewItemBean("代理模式","代理模式",ProxyModeDemoFragment::class.java)
            ,ViewItemBean("Builder模式","Builder模式",BuilderModeDemoFragment::class.java)
            ,ViewItemBean("Delegate模式","Delegate模式",DelegateDemoFragment::class.java)
            ,ViewItemBean("GetService","根据传入的参数类型决定返回参数类型对象",ParamTypeDemoFragment::class.java)
            ,ViewItemBean("Filter模式","Filter模式",FilterDemoFragment::class.java)
            ,ViewItemBean("Chain模式","Chain模式",ChainModeDemoFragment::class.java)
            ,ViewItemBean("Wrapper模式","Wrapper模式", WrapperDesignMode::class.java)
            ,ViewItemBean("IdleHandlerDemo","IdleHandlerDemo", WrapperDesignMode::class.java)

        )
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
    }
}