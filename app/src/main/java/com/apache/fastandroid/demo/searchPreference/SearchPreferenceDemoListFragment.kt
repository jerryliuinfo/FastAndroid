package com.apache.fastandroid.demo.searchPreference

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment


/**
 * Created by Jerry on 2020/10/31.
 */
class SearchPreferenceDemoListFragment : BaseListFragment() {


    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Simple Example", "Simple Example", activity = SimpleExample::class.java),
            ViewItemBean("Enhance Example", "Enhance Example", activity = EnhancedExample::class.java),
            ViewItemBean("NoPreferences Example", "NoPreferences Example", activity = NoPreferencesExample::class.java),
            ViewItemBean("SearchView Example", "SearchView Example", activity = SearchViewExample::class.java),



        )
    }


}