package com.apache.fastandroid.jetpack.lifecycle

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.constraint.practice.ConstraintBasicFragment
import com.apache.fastandroid.demo.round.RoundTextViewFragment
import com.apache.fastandroid.jetpack.lifecycle.traditional.TraditionalLifeCycleFragment
import com.apache.fastandroid.jetpack.lifecycle.traditional.TraditionalLifeCycleListener
import com.tesla.framework.ui.fragment.ABaseFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class JetPackLifeCycleListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("传统生命周期监听", "传统生命周期监听", TraditionalLifeCycleFragment::class.java),
                ViewItemBean("LifeCycle", "LifeCycle", JetPackLifeCycleFragment::class.java)
        )
    }


}