package com.apache.fastandroid.demo.sampleapp

import com.android.example.github.GithubBrowserMainActivity
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.sunflower.fragement.SunFlowerHomeViewPagerFragment
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity

/**
 * Created by Jerry on 2020/11/11.
 */
class SampleAppDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("SunFlower", "SunFlower", SunFlowerHomeViewPagerFragment::class.java,addTitleBar = false)
            ,ViewItemBean("ToUserDaodoApp",         "https://github.com/android/architecture-samples", activity = TasksActivity::class.java,addTitleBar = false)
            ,ViewItemBean("GithubBrowser", "GithubBrowser", activity = GithubBrowserMainActivity::class.java,addTitleBar = true)

        )
    }
}