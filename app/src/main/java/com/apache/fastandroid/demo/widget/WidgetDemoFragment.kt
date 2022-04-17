package com.apache.fastandroid.demo.widget

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.widget.supertextview.SuperTextViewDemoListFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class WidgetDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                //https://github.com/youth5201314/banner
                ViewItemBean("SupterTextView", "SuperTextView", SuperTextViewDemoListFragment::class.java)
        )
    }


}