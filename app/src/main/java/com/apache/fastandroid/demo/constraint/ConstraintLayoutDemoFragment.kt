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
                ViewItemBean("Baseline", "基线对齐", ConstraintBaselineFragment::class.java),
                ViewItemBean("Circle", "角度约束定位", ConstrainCircleFragment::class.java),

                ViewItemBean("Bias", "百分比偏移", ConstraintBiasFragment::class.java),
                ViewItemBean("GoneMargin", "GoneMargin", ConstraintGoneMarginFragment::class.java),
                ViewItemBean("尺寸限制", "min/max Width/Height", ConstrainMinMaxSizeFragment::class.java),
                ViewItemBean("MatchConstraint", "0dp", ConstrainMatchConstraintFragment::class.java),


                ViewItemBean("DimensionRatio", "DimensionRatio", ConstraintDimensionRatioFragment::class.java),


                ViewItemBean("HorizontalChainStyle", "HorizontalChainStyle", ConstraintChainStyleFragment::class.java),
                ViewItemBean("VerticalChainStyle", "VerticalChainStyle", ConstraintVerticalChainStyleFragment::class.java),

                ViewItemBean("Weight权重", "百分比控件", ConstraintWeightFragment::class.java),

                ViewItemBean("Percent", "百分比", ConstraintPercentFragment::class.java),

                ViewItemBean("ConstrainedWidth", " 宽度约束", ConstraintWidthFragment::class.java),
                ViewItemBean("ConstrainedWidthDefault", " 宽度约束", ConstraintWidthDefaultFragment::class.java),

                ViewItemBean("Guideline", "Guideline", ConstraintGuidelinePercentFragment::class.java),


                ViewItemBean("Barrier", "Barrier", ConstraintBarrierFragment::class.java),


                ViewItemBean("Group", "Group", ConstraintGroupFragment::class.java),

                ViewItemBean("PlaceHolder", "PlaceHolder", ConstrainPlaceHolderFragment::class.java),

                ViewItemBean("Space", "Space", ConstraintSpaceFragment::class.java),

                ViewItemBean("Margin", "Margin", ConstraintMarginFragment::class.java),

                ViewItemBean("Layer", "统一设置背景、padding等", ConstraintLayerFragment::class.java),

                ViewItemBean("CircularReaveal", "Circular", ConstrainCircularRevealFragment::class.java),

                ViewItemBean("ConstraintSet", "ConstraintSet", ConstraintSetFragment::class.java),
                ViewItemBean("ConstraintSet切换布局", "ConstraintSet过渡动画", ConstraintSetSwitchLayoutFragment::class.java),
                ViewItemBean("LinerVirtualLayout", "LinerVirtualLayout", ConstraintLinerVirtualLayoutFragment::class.java),

                ViewItemBean("FlowWrapMode", "FlowWrapMode", ConstraintFlowWrapModeFragment::class.java)
        )
    }
}