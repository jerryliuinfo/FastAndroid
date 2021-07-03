package com.apache.fastandroid.demo.recycleview

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2021/5/3.
 */
class BaseRecycleViewAdapterDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("基本用法","基本用法",RecycleViewBasicFragment::class.java)
                ,ViewItemBean("Header","Header",RecycleViewHeaderFootFragment::class.java)
                ,ViewItemBean("EmptyErrorView","EmptyErrorView",RecycleViewEmptyErrorFragment::class.java)
                ,ViewItemBean("下拉刷新上拉加载更多","EmptyErrorView",RecycleViewPullToRefreshFragment::class.java)
                ,ViewItemBean("Animation","Animation",RecycleViewAnimationFragment::class.java)
        )
    }

}