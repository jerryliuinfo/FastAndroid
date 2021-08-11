package com.apache.fastandroid.demo.hencodeplus

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.other.ClipChildFragment
import com.apache.fastandroid.demo.other.ClipToPaddingFragment
import com.apache.fastandroid.demo.constraint.practice.ConstraintBasicFragment
import com.apache.fastandroid.demo.customview.EditTextFragment
import com.apache.fastandroid.demo.customview.NestedScrollViewFragment
import com.apache.fastandroid.demo.nodrawable.LoadingDrawableFragment
import com.apache.fastandroid.demo.nodrawable.NoDrawableFragment
import com.apache.fastandroid.demo.temp.SpanableStringFragment
import java.util.*

/**
 * Created by Jerry on 2020/11/11.
 */
class HencodePlusFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("自定义View绘制", "DashbordView", CustomDrawingFragment::class.java)


        )


    }


}