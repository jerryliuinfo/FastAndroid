package com.apache.fastandroid.demo.widget

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.widget.banner.BannerDemoFragment2
import com.apache.fastandroid.demo.recycleview.BaseRecycleViewAdapterDemoListFragment
import com.apache.fastandroid.demo.widget.qmui.QMUIDemoFragment
import com.apache.fastandroid.demo.widget.supertextview.SuperTextViewDemoListFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class WidgetDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                //https://github.com/youth5201314/banner
                ViewItemBean("Banner", "Banner", BannerDemoFragment2::class.java)
                ,ViewItemBean("BaseRecycleViewAdapter", "BaseRecycleViewAdapter", BaseRecycleViewAdapterDemoListFragment::class.java)
                ,ViewItemBean("QMUI", "QMUI", QMUIDemoFragment::class.java)
                ,ViewItemBean("SupterTextView", "SuperTextView", SuperTextViewDemoListFragment::class.java)
        )
    }


}