package com.apache.fastandroid.demo.component.loadsir

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2021/12/13.
 * https://github.com/KingJA/LoadSir
 */
class LoadSirDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf<ViewItemBean>(
            ViewItemBean("Activity(empty)", "Activity(empty)", LoadSirActivityEmptyFragment::class.java)
            ,ViewItemBean("Activity(ConstraintLayout)", "", LoadSirConstraintLayoutActivity::class.java)
            ,ViewItemBean("Activity(Placeholder)", "Placeholder", LoadSirPlaceholderActivity::class.java)
            ,ViewItemBean("Activity with Convertor", "Activity with Convertor", LoadSirConvertorActivity::class.java)
            ,ViewItemBean("View(Timeout)", "View(Timeout)", ViewTargetActivity::class.java)
            ,ViewItemBean("Default LoadSir Callback", "Default LoadSir Callback", LoadSirDefaultCallbackActivity::class.java)
            ,ViewItemBean("Animate Callback", "Animator Callback", LoadSirAnimatorActivity::class.java)
            ,ViewItemBean("Lottie ", "Lottie", LoadSirLottieActivity::class.java)
        )
    }
}