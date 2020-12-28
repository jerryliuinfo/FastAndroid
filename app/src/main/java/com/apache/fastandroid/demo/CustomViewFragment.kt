package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.other.ClipChildFragment
import com.apache.fastandroid.demo.other.ClipToPaddingFragment
import com.apache.fastandroid.demo.constraint.practice.ConstraintBasicFragment
import com.apache.fastandroid.demo.round.RoundButtonFragment
import com.apache.fastandroid.demo.round.RoundFrameLayoutFragment
import com.apache.fastandroid.demo.round.RoundTextViewFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class CustomViewFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("圆角ImageView", "RoudImageView", ConstraintBasicFragment::class.java),
                ViewItemBean("圆角TextView", "RoundTextView", RoundTextViewFragment::class.java),
                ViewItemBean("圆角Button", "RoundButton", RoundButtonFragment::class.java),
                ViewItemBean("圆角FrameLayout", "RoundFrameLayout", RoundFrameLayoutFragment::class.java),
                ViewItemBean("ClipChild", "ClipChildFragment", ClipChildFragment::class.java),
                ViewItemBean("ClipToPadding", "ClipToPaddingFragment", ClipToPaddingFragment::class.java))
    }


}