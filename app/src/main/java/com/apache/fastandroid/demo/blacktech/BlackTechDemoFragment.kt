package com.apache.fastandroid.demo.blacktech

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.blacktech.viewpump.ViewPumpDemoFragment

/**
 * Created by Jerry on 2021/9/8.
 */
class BlackTechDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("ViewPump","https://github.com/B3nedikt/ViewPump", ViewPumpDemoFragment::class.java)
        )
    }


}