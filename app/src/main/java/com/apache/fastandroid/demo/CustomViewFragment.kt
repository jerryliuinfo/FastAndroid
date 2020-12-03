package com.apache.fastandroid.demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.other.ClipChildFragment
import com.apache.fastandroid.demo.other.ClipToPaddingFragment
import com.apache.fastandroid.demo.round.ConstraintBasicFragment
import com.apache.fastandroid.demo.round.RoundButtonFragment
import com.apache.fastandroid.demo.round.RoundFrameLayoutFragment
import com.apache.fastandroid.demo.round.RoundTextViewFragment

import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.activity_demo_list.*

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