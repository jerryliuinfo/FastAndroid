package com.apache.fastandroid

import com.apache.fastandroid.artemis.ui.BasePageFragment
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.apache.fastandroid.hencoder.basic.R

/**
 * Created by Jerry on 2020/11/11.
 */
class DrawBasicDemoFragment: BasePageFragment() {


    override fun loadPageModels(): MutableList<PageModel> {
        return arrayListOf(
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
            PageModel(R.layout.sample_path, R.string.title_draw_path, R.layout.practice_path),
            PageModel(R.layout.sample_path, "圆角文字", R.layout.practice_path)
        )
    }


}