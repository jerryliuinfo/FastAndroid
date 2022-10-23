package com.apache.fastandroid.demo.blacktech

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.blacktech.easytrack.EasyTrackDemoFragment
import com.apache.fastandroid.demo.blacktech.permissionmonitor.PermissionMonitorFragment
import com.apache.fastandroid.demo.blacktech.sdkeditor.CommonBlackTechFragment
import com.apache.fastandroid.demo.blacktech.spwaitkiller.SpWaitKillerDemoFragment
import com.apache.fastandroid.demo.blacktech.viewpump.ViewPumpDemoFragment

/**
 * Created by Jerry on 2021/9/8.
 */
class BlackTechDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("resString","https://github.com/B3nedikt/restring", ViewPumpDemoFragment::class.java),
            ViewItemBean("ViewPump","https://github.com/B3nedikt/ViewPump", ViewPumpDemoFragment::class.java)
            ,ViewItemBean("EasyTrack","https://github.com/pengxurui/EasyTrack", EasyTrackDemoFragment::class.java)
            ,ViewItemBean("监控App隐私权限方法调用","监控App隐私权限方法调用", PermissionMonitorFragment::class.java)
            ,ViewItemBean("sdk_editor","sdk_editor", CommonBlackTechFragment::class.java)
            ,ViewItemBean("ClickDebounce","ClickDebounce", ClickDebounceFragment::class.java)
            ,ViewItemBean("字符串加密","字符串加密", EncryptDemoFragment::class.java)
            ,ViewItemBean("敏感词检测","houbb/sensitive-word", SensitiveWordDemoFragment::class.java)
            ,ViewItemBean("SpWaitKiller","解决SP卡顿问题", SpWaitKillerDemoFragment::class.java)
            ,ViewItemBean("ResourcesPoet","生成Android XML", ResourcePoetDemoFragment::class.java)
        )
    }


}