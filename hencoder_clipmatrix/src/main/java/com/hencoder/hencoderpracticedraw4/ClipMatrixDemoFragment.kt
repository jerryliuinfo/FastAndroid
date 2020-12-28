package com.hencoder.hencoderpracticedraw4

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_practice_demo.*

/**
 * Created by Jerry on 2020/11/11.
 */
class ClipMatrixDemoFragment: ABaseFragment() {

  /*  pageModels.add(PageModel(R.layout.sample_clip_rect, R.string.title_clip_rect, R.layout.practice_clip_rect))
    pageModels.add(PageModel(R.layout.sample_clip_path, R.string.title_clip_path, R.layout.practice_clip_path))
    pageModels.add(PageModel(R.layout.sample_translate, R.string.title_translate, R.layout.practice_translate))
    pageModels.add(PageModel(R.layout.sample_scale, R.string.title_scale, R.layout.practice_scale))
    pageModels.add(PageModel(R.layout.sample_rotate, R.string.title_rotate, R.layout.practice_rotate))
    pageModels.add(PageModel(R.layout.sample_skew, R.string.title_skew, R.layout.practice_skew))
    pageModels.add(PageModel(R.layout.sample_matrix_translate, R.string.title_matrix_translate, R.layout.practice_matrix_translate))
    pageModels.add(PageModel(R.layout.sample_matrix_scale, R.string.title_matrix_scale, R.layout.practice_matrix_scale))
    pageModels.add(PageModel(R.layout.sample_matrix_rotate, R.string.title_matrix_rotate, R.layout.practice_matrix_rotate))
    pageModels.add(PageModel(R.layout.sample_matrix_skew, R.string.title_matrix_skew, R.layout.practice_matrix_skew))
    pageModels.add(PageModel(R.layout.sample_camera_rotate, R.string.title_camera_rotate, R.layout.practice_camera_rotate))
    pageModels.add(PageModel(R.layout.sample_camera_rotate_fixed, R.string.title_camera_rotate_fixed, R.layout.practice_measure_text))
    pageModels.add(PageModel(R.layout.sample_camera_rotate_hitting_face, R.string.title_camera_rotate_hitting_face, R.layout.practice_camera_rotate_hitting_face))
    pageModels.add(PageModel(R.layout.sample_flipboard, R.string.title_flipboard, R.layout.practice_flipboard))*/


    private val pageModels = arrayListOf(
            PageModel(R.layout.sample_clip_rect, R.string.title_clip_rect, R.layout.practice_clip_rect),
            PageModel(R.layout.sample_clip_path, R.string.title_clip_path, R.layout.practice_clip_path)
    )

    override fun inflateContentView(): Int {
        return R.layout.fragment_practice_demo
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        setToolbarTitle("ClipMatrix")

        pager.adapter = PageAdapter(pageModels,activity!!.supportFragmentManager)
        tabLayout.setupWithViewPager(pager)

    }

}