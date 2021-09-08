package com.apache.fastandroid.jetpack.relearnandroid

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.databinding.bindingadapter.CommonBindingAdapter

/**
 * Created by Jerry on 2021/9/8.
 */
class RelearnAndroidDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("CommonBindingAdapter","DatabindingAdapter通用用法使用", CommonBindingAdapterDemoFragment::class.java)
        )
    }
}