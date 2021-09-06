package com.hencoder.hencoderpracticedraw4

import android.os.Bundle
import android.view.LayoutInflater
import com.tesla.framework.ui.fragment.BaseFragment
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import kotlinx.android.synthetic.main.fragment_practice_demo.*

/**
 * Created by Jerry on 2020/11/11.
 */
class MatrixDemoFragment: BaseFragment() {


    private val pageModels = arrayListOf(
            PageModel(R.layout.sample_clip_rect, R.string.title_clip_rect, R.layout.practice_clip_rect),
            PageModel(R.layout.sample_clip_path, R.string.title_clip_path, R.layout.practice_clip_path),
            PageModel(R.layout.sample_translate, R.string.title_translate, R.layout.practice_translate),
            PageModel(R.layout.sample_scale, R.string.title_scale, R.layout.practice_scale)


    )

    override fun inflateContentView(): Int {
        return R.layout.fragment_practice_demo
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        setToolbarTitle("DrawText")

        pager.adapter = PageAdapter(pageModels,activity!!.supportFragmentManager)
        tabLayout.setupWithViewPager(pager)

    }

}