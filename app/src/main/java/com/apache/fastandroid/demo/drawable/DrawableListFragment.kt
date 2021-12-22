package com.apache.fastandroid.demo.drawable

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.tesla.framework.kt.dp
import com.tesla.framework.kt.dpInt
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.drawable_custom.*

/**
 * Created by Jerry on 2021/12/14.
 */
class DrawableListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("自定义Drawable", "自定义Drawable",DrawableDemoFragment::class.java)
            ,ViewItemBean("BitmapDrawable", "BitmapDrawable",BitmapDrawableFragment::class.java)
        )
    }


}