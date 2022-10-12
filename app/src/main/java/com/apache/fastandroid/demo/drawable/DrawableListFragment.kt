package com.apache.fastandroid.demo.drawable

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2021/12/14.
 */
class DrawableListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("自定义Drawable", "自定义Drawable",DrawableDemoFragment::class.java)
        )
    }


}