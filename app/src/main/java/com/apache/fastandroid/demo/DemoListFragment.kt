package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.amitshekhar.AmitShekharDemoFragment
import com.apache.fastandroid.demo.basic.AndroidBasicDemoFragment
import com.apache.fastandroid.demo.bestpractice.BestPracticeDemoFragment

import com.apache.fastandroid.demo.blacktech.BlackTechDemoFragment
import com.apache.fastandroid.demo.designmode.DesignModeDemoListFragment
import com.apache.fastandroid.demo.drakeet.DrakeetDemoListFragment
import com.apache.fastandroid.demo.guide.GuideDemoListFragment
import com.apache.fastandroid.demo.hitpit.HitPitDemoListFragment
import com.apache.fastandroid.demo.jni.JniDemoListFragment
import com.apache.fastandroid.demo.lyric.LyricDemoFragment
import com.apache.fastandroid.demo.performance.PerformanceDemoFragment
import com.apache.fastandroid.demo.sample.SampleCodeDemoListFragment
import com.apache.fastandroid.demo.sampleapp.SampleAppDemoFragment
import com.apache.fastandroid.demo.storage.StorageDemoListFragment
import com.apache.fastandroid.demo.temp.TempDemoFragment
import com.apache.fastandroid.demo.transition.TransitionDemoFragment
import com.apache.fastandroid.demo.widget.UIWidgetDemoFragment
import com.apache.fastandroid.demo.widget.WidgetDemoFragment
import com.apache.fastandroid.demo.widget.property.UIPropertyDemoListFragment
import com.apache.fastandroid.jetpack.JetPackDemoFragment
import com.apache.fastandroid.jetpack.relearnandroid.RelearnAndroidDemoFragment


/**
 * Created by Jerry on 2020/10/31.
 */
class DemoListFragment : BaseListFragment() {


    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Android官方文档", "Android官方文档", GuideDemoListFragment::class.java),
            ViewItemBean("JetPack", "JetPack", JetPackDemoFragment::class.java),
            ViewItemBean("Google Sample", "Google Sample", SampleAppDemoFragment::class.java),
            ViewItemBean("最佳实践", "最佳实践", BestPracticeDemoFragment::class.java),
            ViewItemBean("工具", "工具", ToolsFragment::class.java),

//            ViewItemBean("Hencoder", "绘制基础", DrawBasicDemoFragment::class.java),
//            ViewItemBean("Hencoder", "绘制Paint", DrawPaintDemoFragment::class.java),
//            ViewItemBean("Hencoder", "绘制文字", DrawTextDemoFragment::class.java),
//            ViewItemBean("Hencoder", "范围裁切", MatrixDemoFragment::class.java),
            ViewItemBean("UI小控件", "UI小控件", UIWidgetDemoFragment::class.java),
            ViewItemBean("UI属性", "UI属性", UIPropertyDemoListFragment::class.java),

            ViewItemBean("CustomViewWidget", "自定义控件", CustomViewFragment::class.java),
            ViewItemBean("系统控件", "系统控件", CustomViewFragment::class.java),

            ViewItemBean("性能优化", "性能优化", PerformanceDemoFragment::class.java),
            ViewItemBean("临时验证", "临时验证", TempDemoFragment::class.java),
            ViewItemBean("Android基础", "Android基础", AndroidBasicDemoFragment::class.java),
            ViewItemBean("开源UI控件", "开源UI控件", WidgetDemoFragment::class.java),
            ViewItemBean("开源框架", "开源框架", OpenSourceDemoFragment::class.java),
            ViewItemBean("设计模式", "设计模式实战", DesignModeDemoListFragment::class.java),
            ViewItemBean("重学Android", "重学Android", RelearnAndroidDemoFragment::class.java),
            ViewItemBean("黑科技", "黑科技", BlackTechDemoFragment::class.java),
            ViewItemBean("Drakeet", "Drakeet", DrakeetDemoListFragment::class.java),
            ViewItemBean("Kotlin语法", "Kotlin语法", KotlinDemoListFragment::class.java),
            ViewItemBean("TransitionMannager", "TransitionMannager", TransitionDemoFragment::class.java),
            ViewItemBean("采坑", "采坑", HitPitDemoListFragment::class.java),
            ViewItemBean("Sample", "Sample", SampleCodeDemoListFragment::class.java),
            ViewItemBean("单元测试", "单元测试", SampleCodeDemoListFragment::class.java),
            ViewItemBean("MVI", "MVI", SampleCodeDemoListFragment::class.java),
            ViewItemBean("各个组件style介绍", "各个组件style介绍", LyricDemoFragment::class.java),
            ViewItemBean("JNI", "JNI", JniDemoListFragment::class.java),
            ViewItemBean("Amit Shekhar", "Amit Shekhar", AmitShekharDemoFragment::class.java),
            ViewItemBean("StorageSample", "StorageSample", StorageDemoListFragment::class.java),


        )
    }


}