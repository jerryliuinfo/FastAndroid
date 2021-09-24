package com.apache.fastandroid.demo.agentweb

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2021/9/23.
 */
class AgentWebDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Activity使用AgentWeb","Activity使用AgentWeb",AgentWebFragment::class.java ),
            ViewItemBean("JS通讯","JS通讯",JSAgentWebFragment::class.java )
        )
    }
}