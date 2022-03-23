package com.apache.fastandroid.demo

import com.apache.fastandroid.DrawBasicDemoFragment
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.basic.AndroidBasicDemoFragment
import com.apache.fastandroid.demo.blacktech.BlackTechDemoFragment
import com.apache.fastandroid.demo.constraint.ConstraintLayoutDemoFragment
import com.apache.fastandroid.demo.coorinator.CoordinatorLayoutDemoFragment
import com.apache.fastandroid.demo.designmode.DesignModeDemoFragment
import com.apache.fastandroid.demo.drakeet.DrakeetDemoListFragment
import com.apache.fastandroid.demo.hitpit.HitPitDemoListFragment
import com.apache.fastandroid.jetpack.JetPackDemoFragment
import com.apache.fastandroid.demo.lyric.LyricDemoFragment
import com.apache.fastandroid.demo.performance.PerformanceDemoFragment
import com.apache.fastandroid.demo.sample.SampleCodeDemoListFragment
import com.apache.fastandroid.demo.tablayout.ArticleTabsFragment
import com.apache.fastandroid.demo.temp.TempDemoFragment
import com.apache.fastandroid.demo.transition.TransitionDemoFragment
import com.apache.fastandroid.demo.widget.UIWidgetDemoFragment
import com.apache.fastandroid.demo.widget.WidgetDemoFragment
import com.apache.fastandroid.jetpack.relearnandroid.RelearnAndroidDemoFragment
import com.hencoder.hencoderpracticedraw2.DrawPaintDemoFragment
import com.hencoder.hencoderpracticedraw3.DrawTextDemoFragment
import com.hencoder.hencoderpracticedraw4.MatrixDemoFragment

/**
 * Created by Jerry on 2020/10/31.
 */
class DemoListFragment : BaseListFragment() {


    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("JetPack", "JetPack", JetPackDemoFragment::class.java),

            ViewItemBean("Hencoder", "绘制基础", DrawBasicDemoFragment::class.java),
            ViewItemBean("Hencoder", "绘制Paint", DrawPaintDemoFragment::class.java),
            ViewItemBean("Hencoder", "绘制文字", DrawTextDemoFragment::class.java),
            ViewItemBean("Hencoder", "范围裁切", MatrixDemoFragment::class.java),
            ViewItemBean("UI小控件", "UI小控件", UIWidgetDemoFragment::class.java),

            ViewItemBean("CustomViewWidget", "自定义控件", CustomViewFragment::class.java),
            ViewItemBean("ConstraintLayout", "约束布局", ConstraintLayoutDemoFragment::class.java),
            ViewItemBean("CoordinatorLayout", "CoordinatorLayout", CoordinatorLayoutDemoFragment::class.java),
            ViewItemBean("性能优化", "性能优化", PerformanceDemoFragment::class.java),
            ViewItemBean("临时验证", "临时验证", TempDemoFragment::class.java),
            ViewItemBean("Android基础", "Android基础", AndroidBasicDemoFragment::class.java),
            ViewItemBean("开源UI控件", "开源UI控件", WidgetDemoFragment::class.java),
            ViewItemBean("开源框架", "开源框架", OpenSourceDemoFragment::class.java),
            ViewItemBean("设计模式", "设计模式实战", DesignModeDemoFragment::class.java),
            ViewItemBean("重学Android", "重学Android", RelearnAndroidDemoFragment::class.java),
            ViewItemBean("黑科技", "黑科技", BlackTechDemoFragment::class.java),
            ViewItemBean("Drakeet", "Drakeet", DrakeetDemoListFragment::class.java),
            ViewItemBean("Kotlin语法", "Kotlin语法", KotlinDemoListFragment::class.java)
            ,ViewItemBean("TransitionMannager", "TransitionMannager", TransitionDemoFragment::class.java)
            ,ViewItemBean("采坑", "采坑", HitPitDemoListFragment::class.java)
            ,ViewItemBean("Sample", "Sample", SampleCodeDemoListFragment::class.java)
            ,ViewItemBean("单元测试", "单元测试", SampleCodeDemoListFragment::class.java)
            ,ViewItemBean("TabLayoutFragment", "TabLayoutFragment", ArticleTabsFragment::class.java)
            ,ViewItemBean("PreferenceFragment", "PreferenceFragment", PreferenceFragmentDemo::class.java)
            ,ViewItemBean("Lyric", "Lyric", LyricDemoFragment::class.java)


        )
    }


}