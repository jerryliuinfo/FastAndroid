package com.apache.fastandroid.demo.databinding

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.databinding.practice.*

/**
 *
 * 参考:https://github.com/android/databinding-samples
 */
class DatabindingDemoFragment:BaseListFragment() {



    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("ObservableField","ObservableField",DatabindingObservableFiledFragment::class.java)
                ,ViewItemBean("ViewModel","ViewModel",DatabindingViewModelFragment::class.java)


        )
    }

}