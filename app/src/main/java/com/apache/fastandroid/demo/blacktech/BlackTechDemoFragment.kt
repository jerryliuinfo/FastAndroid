package com.apache.fastandroid.demo.blacktech

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.databinding.bindingadapter.CommonBindingAdapter

/**
 * Created by Jerry on 2021/9/8.
 */
class BlackTechDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("AspectJ","AspectJ", AspectJDemoFragment::class.java)
        )
    }


}