package com.hencoder.hencoderpracticedraw3

import android.os.Bundle
import android.view.LayoutInflater
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import kotlinx.android.synthetic.main.fragment_practice_demo.*

/**
 * Created by Jerry on 2020/11/11.
 */
class DrawTextDemoFragment: BaseStatusFragmentNew() {


    private val pageModels = arrayListOf(
            PageModel(R.layout.practice_run_advance_view, R.string.title_get_run_advance, R.layout.practice_run_advance_view),
            PageModel(R.layout.practice_measure_text, R.string.title_measure_text, R.layout.practice_measure_text),
            PageModel(R.layout.practice_get_font_metrics, R.string.title_get_font_metrics, R.layout.practice_get_font_metrics),
            PageModel(R.layout.practice_get_font_spacing, R.string.title_get_font_spacing, R.layout.practice_get_font_spacing),
            PageModel(R.layout.practice_draw_text, R.string.title_draw_text, R.layout.practice_draw_text),
            PageModel(R.layout.practice_static_layout, R.string.title_static_layout, R.layout.practice_static_layout),
            PageModel(R.layout.practice_set_text_size, R.string.title_set_text_size, R.layout.practice_set_text_size),
            PageModel(R.layout.practice_set_typeface, R.string.title_set_typeface, R.layout.practice_set_typeface),
            PageModel(R.layout.practice_set_fake_bold_text, R.string.title_set_fake_bold_text, R.layout.practice_set_fake_bold_text),
            PageModel(R.layout.practice_set_strike_thru_text, R.string.title_set_strike_thru_text, R.layout.practice_set_strike_thru_text),
            PageModel(R.layout.practice_set_text_skew_x, R.string.title_set_text_skew_x, R.layout.practice_set_text_skew_x),
            PageModel(R.layout.practice_get_font_spacing, R.string.title_get_font_spacing, R.layout.practice_get_font_spacing),
            PageModel(R.layout.practice_set_text_align, R.string.title_set_text_align, R.layout.practice_set_text_align)

    )

    override fun getLayoutId(): Int {
        return R.layout.fragment_practice_demo
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        setToolbarTitle("DrawText")

        pager.adapter = PageAdapter(pageModels,activity!!.supportFragmentManager)
        tabLayout.setupWithViewPager(pager)

    }

}