package com.apache.fastandroid.artemis.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.apache.fastandroid.artemis.ui.PageFragment
import com.apache.fastandroid.artemis.ui.bean.PageModel

/**
 * Created by Jerry on 2020/11/11.
 */
class PageAdapter(private val pageModels: List<PageModel>, fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return PageFragment.newInstance(pageModels[position].sampleLayoutRes,pageModels[position].practiceLayoutRes)
    }

    override fun getCount(): Int {
        return pageModels.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageModels[position].titleStr
    }
}