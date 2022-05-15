package com.apache.fastandroid.demo.sampleapp

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.cheese.CheeseActivity
import com.apache.fastandroid.demo.databinding.DatabindingDemoFragment
import com.apache.fastandroid.demo.room.RoomDemoFragment
import com.apache.fastandroid.demo.sunflower.fragement.SunFlowerHomeViewPagerFragment
import com.apache.fastandroid.jetpack.coroutine.CoroutineDemoListFragment
import com.apache.fastandroid.jetpack.flow.FlowDemoListFragment
import com.apache.fastandroid.jetpack.hit.HitDemoFragment
import com.apache.fastandroid.jetpack.navigation.NavigationDemoActivity
import com.apache.fastandroid.jetpack.lifecycle.JetPackLifeCycleDemoFragment
import com.apache.fastandroid.jetpack.livedata.*
import com.apache.fastandroid.jetpack.navigation.NavigationDrawerDemoActivity2
import com.apache.fastandroid.jetpack.navigation.advance.AdvanceNavigationActivity
import com.apache.fastandroid.jetpack.navigation.drawer.NavigationDrawerDemoActivity
import com.apache.fastandroid.jetpack.viewmodel.JetPackViewModelListFragment
import com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity

/**
 * Created by Jerry on 2020/11/11.
 */
class SampleAppDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("SunFlower", "SunFlower", SunFlowerHomeViewPagerFragment::class.java,addTitleBar = false)
            ,ViewItemBean("TodoApp", "TodoApp", activity = TasksActivity::class.java,addTitleBar = false)

        )
    }
}