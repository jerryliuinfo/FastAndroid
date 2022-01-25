package com.apache.fastandroid.demo.blacktech

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.blacktech.permissionmonitor.PermissionMonitorFragment
import com.apache.fastandroid.demo.blacktech.sdkeditor.CommonBlackTechFragment
import com.apache.fastandroid.demo.blacktech.viewpump.ViewPumpDemoFragment

/**
 * Created by Jerry on 2021/9/8.
 */
class BlackTechDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("ViewPump","https://github.com/B3nedikt/ViewPump", ViewPumpDemoFragment::class.java)
            ,ViewItemBean("监控App隐私权限方法调用","监控App隐私权限方法调用", PermissionMonitorFragment::class.java)
            ,ViewItemBean("sdk_editor","sdk_editor", CommonBlackTechFragment::class.java)
        )
    }


}