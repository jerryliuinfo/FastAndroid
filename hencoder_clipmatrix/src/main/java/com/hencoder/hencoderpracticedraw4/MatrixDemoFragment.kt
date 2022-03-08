package com.hencoder.hencoderpracticedraw4

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.artemis.ui.BasePageFragment
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.hencoder.hencoderpracticedraw4.databinding.FragmentPracticeDemoBinding
import com.tesla.framework.ui.fragment.BaseVMFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class MatrixDemoFragment: BasePageFragment() {

    override fun loadPageModels(): MutableList<PageModel> {
        return arrayListOf(
            PageModel(R.layout.sample_clip_rect, R.string.title_clip_rect, R.layout.practice_clip_rect)
            ,PageModel(R.layout.sample_clip_path, R.string.title_clip_path, R.layout.practice_clip_path)
            ,PageModel(R.layout.sample_translate, R.string.title_translate, R.layout.practice_translate)
            ,PageModel(R.layout.sample_scale, R.string.title_scale, R.layout.practice_scale)
            ,PageModel(R.layout.sample_rotate, R.string.title_rotate, R.layout.practice_rotate)
            ,PageModel(R.layout.sample_skew, R.string.title_skew, R.layout.practice_skew)
            ,PageModel(R.layout.sample_matrix_translate, R.string.title_matrix_translate, R.layout.practice_matrix_translate)
            ,PageModel(R.layout.sample_matrix_scale, R.string.title_matrix_scale, R.layout.practice_matrix_scale)
            ,PageModel(R.layout.sample_matrix_rotate, R.string.title_matrix_rotate, R.layout.practice_matrix_rotate)
            ,PageModel(R.layout.sample_matrix_skew, R.string.title_matrix_skew, R.layout.practice_matrix_skew)
            ,PageModel(R.layout.sample_camera_rotate, R.string.title_camera_rotate, R.layout.practice_camera_rotate)
            ,PageModel(R.layout.sample_camera_rotate_fixed, R.string.title_camera_rotate_fixed, R.layout.practice_camera_rotate_fixed)


        )
    }




}