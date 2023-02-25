package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.constraint.ConstraintLayoutDemoFragment
import com.apache.fastandroid.demo.coorinator.CoordinatorLayoutDemoFragment
import com.apache.fastandroid.demo.customview.EditTextFragment
import com.apache.fastandroid.demo.customview.NestedScrollViewFragment
import com.apache.fastandroid.demo.drawable.DrawableListFragment
import com.apache.fastandroid.demo.nodrawable.LoadingDrawableFragment
import com.apache.fastandroid.demo.nodrawable.NoDrawableFragment
import com.apache.fastandroid.demo.other.ClipChildFragment
import com.apache.fastandroid.demo.other.ClipChildFragment2
import com.apache.fastandroid.demo.other.ClipToPaddingFragment
import com.apache.fastandroid.demo.other.UserfulPropertyFragment
import com.apache.fastandroid.demo.roudview.RoundViewDemoFragment
import com.apache.fastandroid.demo.shapeimageview.ShapeImageviewDemoFragment
import com.apache.fastandroid.demo.snaphelper.LinerSnapHelperDemoFragment
import com.apache.fastandroid.demo.snaphelper.NoSnapHelperDemoFragment
import com.apache.fastandroid.demo.snaphelper.PagerSnapHelperDemoFragment
import com.apache.fastandroid.demo.tablayout.ArticleTabsFragment
import com.apache.fastandroid.demo.temp.SpanableStringFragment
import com.apache.fastandroid.demo.widget.LinearProgressIndicatorDemoFragment
import com.apache.fastandroid.demo.widget.ListAdapterFragment
import com.apache.fastandroid.demo.widget.lyric.LyricFragment
import com.apache.fastandroid.demo.widget.mergeadapter.MergeAdapterDemoFragment
import com.apache.fastandroid.demo.widget.spansize.GridLayoutManagerSpanSizeDemoFragment
import com.apache.fastandroid.demo.widget.system.SystemWidgetFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class CustomViewFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("ConstraintLayout", "约束布局", ConstraintLayoutDemoFragment::class.java),
                ViewItemBean("CoordinatorLayout", "CoordinatorLayout", CoordinatorLayoutDemoFragment::class.java),
                ViewItemBean("ClipChild", "ClipChildFragment", ClipChildFragment::class.java),
                ViewItemBean("ClipChild2", "ClipChildFragment2", ClipChildFragment2::class.java),
                ViewItemBean("ClipToPadding", "ClipToPaddingFragment", ClipToPaddingFragment::class.java),
                ViewItemBean("有用的属性", "有用的属性", UserfulPropertyFragment::class.java),
                ViewItemBean("SpanableString", "SpanableString", SpanableStringFragment::class.java)
                ,ViewItemBean("EditText", "EditText", EditTextFragment::class.java)
                ,ViewItemBean("NestedScrollView", "NestedScrollView", NestedScrollViewFragment::class.java)
                ,ViewItemBean("NoDrawable", "NoDrawable", NoDrawableFragment::class.java)
                ,ViewItemBean("LoadingDrawable", "LoadingDrawable", LoadingDrawableFragment::class.java)
                ,ViewItemBean("ShapeableImageView", "ShapeableImageView", ShapeImageviewDemoFragment::class.java)
                ,ViewItemBean("CustomRoundTextview", "CustomRoundTextview", RoundViewDemoFragment::class.java)
                ,ViewItemBean("Drawable", "Drawable", DrawableListFragment::class.java)
                ,ViewItemBean("RecycleviewDivider", "RecycleviewDivider", DrawableListFragment::class.java)
                ,ViewItemBean("Lyric", "Lyric", LyricFragment::class.java)
                ,ViewItemBean("TabLayoutFragment", "TabLayoutFragment", ArticleTabsFragment::class.java)
                ,ViewItemBean("BottomTabFragment", "BottomTabFragment", ArticleTabsFragment::class.java)
                ,ViewItemBean("PreferenceFragment", "PreferenceFragment", PreferenceFragmentDemo::class.java)
                ,ViewItemBean("TextSwitch", "TextSwitch", PreferenceFragmentDemo::class.java)
                ,ViewItemBean("TextInputEditText", "TextInputEditText", SystemWidgetFragment::class.java)
                ,ViewItemBean("LinearProgressIndicator", "LinearProgressIndicator", LinearProgressIndicatorDemoFragment::class.java,addTitleBar = false)
                ,ViewItemBean("NoSnapHelper", "NoSnapHelper", NoSnapHelperDemoFragment::class.java,addTitleBar = true)
                ,ViewItemBean("LinerSnapHelper", "LinerSnapHelper", LinerSnapHelperDemoFragment::class.java,addTitleBar = true)
                ,ViewItemBean("PageSnapHelper", "PageSnapHelper", PagerSnapHelperDemoFragment::class.java,addTitleBar = true)
                ,ViewItemBean("MergeAdapter", "MergeAdapter", MergeAdapterDemoFragment::class.java,addTitleBar = true)
                ,ViewItemBean("GridLayoutManager", "SpanSizeLookup", GridLayoutManagerSpanSizeDemoFragment::class.java,addTitleBar = true)
                ,ViewItemBean("RVListAdapter", "RVListAdapter", ListAdapterFragment::class.java,addTitleBar = true)
                ,ViewItemBean("WebView", "WebView", WebViewFragment::class.java,addTitleBar = true)


        )


    }


}