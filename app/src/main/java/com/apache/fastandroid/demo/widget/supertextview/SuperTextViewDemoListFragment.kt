package com.apache.fastandroid.demo.widget.supertextview

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.ui.adapter.PageAdapter
import com.apache.fastandroid.artemis.ui.bean.PageModel
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.fragment_practice_demo.*

/**
 * Created by Jerry on 2021/5/18.
 */
class SuperTextViewDemoListFragment: BaseStatusFragmentNew() {

    private val pageModels = arrayListOf(
            PageModel(R.layout.stv_superbutton, "SuperButton")

    )

    override fun getLayoutId(): Int {
        return R.layout.fragment_practice_demo
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        setToolbarTitle("DrawText")

        pager.adapter = PageAdapter(pageModels,activity!!.supportFragmentManager,true)
        tabLayout.setupWithViewPager(pager)

    }

}