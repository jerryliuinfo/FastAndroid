package com.apache.fastandroid.demo.databinding

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.databinding.practice.*

/**
 * Created by Jerry on 2021/1/11.
 */
class DatabindingFragmentDemo:BaseListFragment() {



    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("单项绑定","数据绑定",DatebinDingBasicDemoFragment::class.java)
                ,ViewItemBean("单项绑定","Observable",     DatabinDingObservableDemoFragment::class.java)
                ,ViewItemBean("单项绑定","ObservableField",DatabinDingObservableFieldDemoFragment::class.java)
                ,ViewItemBean("双项绑定","双项绑定",DatabinDingTwoWayDemoFragment::class.java)
                ,ViewItemBean("事件绑定","事件绑定",DatabinDingEventFragment::class.java)
                ,ViewItemBean("BindingAdapter","BindingAdapter",DatabinDingBindingAdapterFragment::class.java)
                ,ViewItemBean("结合 LiveData","结合 LiveData",DatabinDingViewModelFragment::class.java)
        )
    }

}