package com.hencoder.hencoderpracticedraw2

import com.apache.fastandroid.artemis.ui.BasePageFragment
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.apache.fastandroid.hencoder.paint.R

/**
 * Created by Jerry on 2020/11/11.
 */
class DrawPaintDemoFragment: BasePageFragment() {

    override fun loadPageModels(): MutableList<PageModel> {
        return arrayListOf(
            PageModel(R.layout.sample_linear_gradient, R.string.title_linear_gradient, R.layout.practice_linear_gradient),
            PageModel(R.layout.sample_radial_gradient, R.string.title_radial_gradient, R.layout.practice_radial_gradient),
            PageModel(R.layout.sample_sweep_gradient, R.string.title_sweep_gradient, R.layout.practice_sweep_gradient),
            PageModel(R.layout.sample_bitmap_shader, R.string.title_bitmap_shader, R.layout.practice_bitmap_shader),
            PageModel(R.layout.sample_compose_shader, R.string.title_compose_shader, R.layout.practice_compose_shader),
            PageModel(R.layout.sample_lighting_color_filter, R.string.title_lighting_color_filter, R.layout.practice_lighting_color_filter),
            PageModel(R.layout.sample_color_mask_color_filter, R.string.title_color_matrix_color_filter, R.layout.practice_color_matrix_color_filter),
            PageModel(R.layout.sample_xfermode, R.string.title_xfermode, R.layout.practice_xfermode),
            PageModel(R.layout.sample_stroke_cap, R.string.title_stroke_cap, R.layout.practice_stroke_cap),
            PageModel(R.layout.sample_path_effect, R.string.title_path_effect, R.layout.practice_path_effect),
            PageModel(R.layout.sample_shadow_layer, R.string.title_shader_layer, R.layout.practice_shadow_layer),
            PageModel(R.layout.sample_mask_filter, R.string.title_mask_filter, R.layout.practice_mask_filter),
            PageModel(R.layout.sample_text_path, R.string.title_text_path, R.layout.practice_text_path),
            PageModel(R.layout.sample_fill_path, R.string.title_fill_path, R.layout.practice_fill_path)
        )
    }


}