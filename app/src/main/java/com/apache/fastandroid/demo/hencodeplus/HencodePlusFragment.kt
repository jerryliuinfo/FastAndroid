package com.apache.fastandroid.demo.hencodeplus

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class HencodePlusFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("自定义View绘制", "DashbordView", CustomDrawingFragment::class.java)


        )


    }


}