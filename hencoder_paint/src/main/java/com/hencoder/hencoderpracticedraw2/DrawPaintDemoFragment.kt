package com.hencoder.hencoderpracticedraw2

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.apache.fastandroid.hencoder.paint.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_draw_paint_demo.*

/**
 * Created by Jerry on 2020/11/11.
 */
class DrawPaintDemoFragment:ABaseFragment() {

  /*  pageModels.add(PageModel(R.layout.sample_linear_gradient, R.string.title_linear_gradient, R.layout.practice_linear_gradient))
    pageModels.add(PageModel(R.layout.sample_radial_gradient, R.string.title_radial_gradient, R.layout.practice_radial_gradient))
    pageModels.add(PageModel(R.layout.sample_sweep_gradient, R.string.title_sweep_gradient, R.layout.practice_sweep_gradient))
    pageModels.add(PageModel(R.layout.sample_bitmap_shader, R.string.title_bitmap_shader, R.layout.practice_bitmap_shader))
    pageModels.add(PageModel(R.layout.sample_compose_shader, R.string.title_compose_shader, R.layout.practice_compose_shader))
    pageModels.add(PageModel(R.layout.sample_lighting_color_filter, R.string.title_lighting_color_filter, R.layout.practice_lighting_color_filter))
    pageModels.add(PageModel(R.layout.sample_color_mask_color_filter, R.string.title_color_matrix_color_filter, R.layout.practice_color_matrix_color_filter))
    pageModels.add(PageModel(R.layout.sample_xfermode, R.string.title_xfermode, R.layout.practice_xfermode))
    pageModels.add(PageModel(R.layout.sample_stroke_cap, R.string.title_stroke_cap, R.layout.practice_stroke_cap))
    pageModels.add(PageModel(R.layout.sample_stroke_join, R.string.title_stroke_join, R.layout.practice_stroke_join))
    pageModels.add(PageModel(R.layout.sample_stroke_miter, R.string.title_stroke_miter, R.layout.practice_stroke_miter))
    pageModels.add(PageModel(R.layout.sample_path_effect, R.string.title_path_effect, R.layout.practice_path_effect))
    pageModels.add(PageModel(R.layout.sample_shadow_layer, R.string.title_shader_layer, R.layout.practice_shadow_layer))
    pageModels.add(PageModel(R.layout.sample_mask_filter, R.string.title_mask_filter, R.layout.practice_mask_filter))
    pageModels.add(PageModel(R.layout.sample_fill_path, R.string.title_fill_path, R.layout.practice_fill_path))
    pageModels.add(PageModel(R.layout.sample_text_path, R.string.title_text_path, R.layout.practice_text_path))*/

    private val pageModels = arrayListOf(
            PageModel(R.layout.sample_linear_gradient, R.string.title_linear_gradient, R.layout.practice_linear_gradient),
            PageModel(R.layout.sample_radial_gradient, R.string.title_radial_gradient, R.layout.practice_radial_gradient),
            PageModel(R.layout.sample_sweep_gradient, R.string.title_sweep_gradient, R.layout.practice_sweep_gradient),
            PageModel(R.layout.sample_bitmap_shader, R.string.title_bitmap_shader, R.layout.practice_bitmap_shader),
            PageModel(R.layout.sample_bitmap_shader, R.string.title_circle_image_drawable, R.layout.practice_bitmap_circle_image_drawable)
//            PageModel(R.layout.sample_lighting_color_filter, R.string.title_lighting_color_filter, R.layout.practice_lighting_color_filter),
//            PageModel(R.layout.sample_color_mask_color_filter, R.string.title_draw_oval, R.layout.practice_oval),
//            PageModel(R.layout.sample_xfermode, R.string.title_draw_line, R.layout.practice_line),
//            PageModel(R.layout.sample_stroke_cap, R.string.title_draw_round_rect, R.layout.practice_round_rect),
//            PageModel(R.layout.sample_stroke_join, R.string.title_draw_arc, R.layout.practice_arc),
//            PageModel(R.layout.sample_stroke_miter, R.string.title_draw_path, R.layout.practice_path),
//            PageModel(R.layout.sample_path_effect, R.string.title_draw_path, R.layout.practice_path),
//            PageModel(R.layout.sample_shadow_layer, R.string.title_draw_path, R.layout.practice_path),
//            PageModel(R.layout.sample_mask_filter, R.string.title_draw_path, R.layout.practice_path),
//            PageModel(R.layout.sample_fill_path, R.string.title_draw_path, R.layout.practice_path),
//            PageModel(R.layout.sample_text_path, R.string.title_draw_path, R.layout.practice_path),
    )

    override fun inflateContentView(): Int {
        return R.layout.fragment_draw_paint_demo
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        pager.adapter = PageAdapter(pageModels,activity!!.supportFragmentManager)
        tabLayout.setupWithViewPager(pager)

    }


}