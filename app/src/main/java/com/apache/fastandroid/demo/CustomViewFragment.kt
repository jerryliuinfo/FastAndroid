package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.other.ClipChildFragment
import com.apache.fastandroid.demo.other.ClipToPaddingFragment
import com.apache.fastandroid.demo.constraint.practice.ConstraintBasicFragment
import com.apache.fastandroid.demo.customview.EditTextFragment
import com.apache.fastandroid.demo.customview.NestedScrollViewFragment
import com.apache.fastandroid.demo.nodrawable.NoDrawableFragment
import com.apache.fastandroid.demo.temp.SpanableStringFragment
import java.util.*

/**
 * Created by Jerry on 2020/11/11.
 */
class CustomViewFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("ClipChild", "ClipChildFragment", ClipChildFragment::class.java),
                ViewItemBean("ClipToPadding", "ClipToPaddingFragment", ClipToPaddingFragment::class.java)
                ,ViewItemBean("SpanableString", "SpanableString", SpanableStringFragment::class.java)
                ,ViewItemBean("EditText", "EditText", EditTextFragment::class.java)
                ,ViewItemBean("NestedScrollView", "NestedScrollView", NestedScrollViewFragment::class.java)
                ,ViewItemBean("NoDrawable", "NoDrawable", NoDrawableFragment::class.java)

        )


    }


}