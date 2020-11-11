package com.apache.fastandroid.hencoder

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_practice_demo.*

/**
 * Created by Jerry on 2020/11/11.
 */
class Practice1DemoFragment:ABaseFragment() {

    private val pageModels = arrayListOf(
            PageModel(R.layout.sample_color, R.string.title_draw_color, R.layout.practice_color),
            PageModel(R.layout.sample_circle, R.string.title_draw_circle, R.layout.practice_circle)

    )

    override fun inflateContentView(): Int {
        return R.layout.fragment_practice_demo
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        pager.adapter = PageAdapter(pageModels,activity!!.supportFragmentManager)
        tabLayout.setupWithViewPager(pager)

    }


}