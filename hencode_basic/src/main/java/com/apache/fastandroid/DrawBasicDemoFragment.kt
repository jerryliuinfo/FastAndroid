package com.apache.fastandroid

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.apache.fastandroid.hencoder.basic.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_practice_demo.*

/**
 * Created by Jerry on 2020/11/11.
 */
class DrawBasicDemoFragment:ABaseFragment() {

    private val pageModels = arrayListOf(
            PageModel(R.layout.sample_pie_chart, R.string.title_draw_pie_chart, R.layout.practice_pie_chart),
            PageModel(R.layout.sample_histogram, R.string.title_draw_histogram, R.layout.practice_histogram2),
            PageModel(R.layout.sample_color, R.string.title_draw_color, R.layout.practice_color),
            PageModel(R.layout.sample_circle, R.string.title_draw_circle, R.layout.practice_circle),
            PageModel(R.layout.sample_rect, R.string.title_draw_rect, R.layout.practice_rect),
            PageModel(R.layout.sample_point, R.string.title_draw_point, R.layout.practice_point),
            PageModel(R.layout.sample_oval, R.string.title_draw_oval, R.layout.practice_oval),
            PageModel(R.layout.sample_line, R.string.title_draw_line, R.layout.practice_line),
            PageModel(R.layout.sample_round_rect, R.string.title_draw_round_rect, R.layout.practice_round_rect),
            PageModel(R.layout.sample_arc, R.string.title_draw_arc, R.layout.practice_arc),
            PageModel(R.layout.sample_path, R.string.title_draw_path, R.layout.practice_path)
    )

    override fun inflateContentView(): Int {
        return R.layout.fragment_practice_demo
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        setToolbarTitle("绘制基础")


        pager.adapter = PageAdapter(pageModels,activity!!.supportFragmentManager)
        tabLayout.setupWithViewPager(pager)

    }


}