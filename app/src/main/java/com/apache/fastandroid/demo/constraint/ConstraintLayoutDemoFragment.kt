package com.apache.fastandroid.demo.constraint

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.constraint.practice.*

/**
 * Created by Jerry on 2020/11/11.
 */
class ConstraintLayoutDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("约束布局", "约束布局基本使用1", ConstraintBasicFragment::class.java),
                ViewItemBean("约束布局", "约束布局基本使用2", ConstraintBasicFragment2::class.java),
                ViewItemBean("传统布局居中", "RelativeLayout居中", ConstraintCenterRelativeLayoutFragment::class.java),
                ViewItemBean("约束布局居中", "居中2", ConstraintCenterFragment::class.java),
                ViewItemBean("约束布局居中", "居中2", ConstraintCenter2Fragment::class.java),
                ViewItemBean("Weight权重", "百分比控件", ConstraintWeightFragment::class.java),
                ViewItemBean("Baseline", "基线", ConstraintBaselineFragment::class.java),
                ViewItemBean("Circle", "角度定位", ConstrainCircleFragment::class.java),
                ViewItemBean("ConstrainedWidth", " 宽度约束", ConstraintWidthFragment::class.java),
                ViewItemBean("Bias", "偏移", ConstraintBiasFragment::class.java),
                ViewItemBean("Bias2", "偏移2", ConstraintBias2Fragment::class.java),
                ViewItemBean("GoneMargin", "GoneMargin", ConstraintGoneMarginFragment::class.java),
                ViewItemBean("ChainStyle", "约束链风格", ConstraintChainStyleFragment::class.java),
                ViewItemBean("DimensionRatio", "DimensionRatio", ConstraintDimensionRatioFragment::class.java),
                ViewItemBean("Percent", "百分比", ConstraintPercentFragment::class.java),
                ViewItemBean("Percent", "百分比", ConstraintPercent2Fragment::class.java),
                ViewItemBean("GuidelinePercent", "GuidelinePercent", ConstraintGuidelinePercentFragment::class.java),
                ViewItemBean("Guideline", "GuidelineBeginEnd", ConstraintGuidelineBeginEndFragment::class.java),
                ViewItemBean("Group", "Group", ConstraintGroupFragment::class.java),
                ViewItemBean("Space", "Space", ConstraintSpaceFragment::class.java),
                ViewItemBean("Margin", "Margin", ConstraintMarginFragment::class.java),
                ViewItemBean("Layer", "Layer", ConstraintLayerFragment::class.java),
                ViewItemBean("Barrier", "Barrier", ConstraintBarrierFragment::class.java),
                ViewItemBean("CircularReaveal", "Circular", ConstrainCircularRevealFragment::class.java),
                ViewItemBean("PlaceHolder", "PlaceHolder", ConstrainPlaceHolderFragment::class.java),
                ViewItemBean("ConstraintSet", "ConstraintSet", ConstraintSetFragment::class.java),
                ViewItemBean("ConstraintSet切换布局", "ConstraintSet过渡动画", ConstraintSetSwitchLayoutFragment::class.java),
                ViewItemBean("LinerVirtualLayout", "LinerVirtualLayout", ConstraintLinerVirtualLayoutFragment::class.java),
                ViewItemBean("Flow", "Flow_Vertical", ConstraintFlowVerticalFragment::class.java),
                ViewItemBean("Flow", "Flow_Horizontal", ConstraintFlowHorizontalFragment::class.java)

                )
    }
}