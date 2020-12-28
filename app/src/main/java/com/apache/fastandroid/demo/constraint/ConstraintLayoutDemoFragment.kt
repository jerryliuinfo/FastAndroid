package com.apache.fastandroid.demo.constraint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.constraint.practice.*
import com.tesla.framework.ui.activity.FragmentArgs

import com.tesla.framework.ui.activity.FragmentContainerActivity
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.activity_demo_list.*

/**
 * Created by Jerry on 2020/11/11.
 */
class ConstraintLayoutDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("约束布局", "约束布局基本使用1", ConstraintBasicFragment::class.java),
                ViewItemBean("约束布局", "约束布局基本使用2", ConstraintBasicFragment2::class.java),
                ViewItemBean("约束布局居中", "居中2", ConstraintCenterFragment::class.java),
                ViewItemBean("约束布局居中", "居中2", ConstraintCenter2Fragment::class.java),
                ViewItemBean("Weight权重", "Weight权重", ConstraintWeightFragment::class.java),
                ViewItemBean("Baseline", "Baseline", ConstraintBaselineFragment::class.java),
                ViewItemBean("角度定位", "角度定位", ConstrainCircularFragment::class.java),
                ViewItemBean("宽度约束", "ConstrainedWidth", ConstraintWidthFragment::class.java),
                ViewItemBean("Bias", "Bias", ConstraintBiasFragment::class.java),
                ViewItemBean("GoneMargin", "GoneMargin", ConstraintGoneMarginFragment::class.java),
                ViewItemBean("ChainStyle", "ChainStyle", ConstraintChainStyleFragment::class.java),
                ViewItemBean("DimensionRatio", "DimensionRatio", ConstraintDimensionRatioFragment::class.java),
                ViewItemBean("Percent", "Percent", ConstraintPercentFragment::class.java),
                ViewItemBean("Guideline", "Guideline", ConstraintGuidelineFragment::class.java),
                ViewItemBean("Group", "Group", ConstraintGroupFragment::class.java),
                ViewItemBean("Margin", "Margin", ConstraintMarginFragment::class.java),
                ViewItemBean("传统布局居中", "RelativeLayout居中", ConstraintCenterRelativeLayoutFragment::class.java)
        )
    }
}