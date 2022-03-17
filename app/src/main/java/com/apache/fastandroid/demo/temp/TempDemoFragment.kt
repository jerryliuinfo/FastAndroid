package com.apache.fastandroid.demo.temp

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.LayoutInflaterDemoFragment
import com.apache.fastandroid.demo.launchermode.LaunchModeDemoFragment
import com.apache.fastandroid.demo.snaphelper.SnapHelperDemoFragment
import com.apache.fastandroid.demo.style.FitSystemWindowDemoActivity
import com.apache.fastandroid.demo.style.FitSystemWindowDemoActivity2
import com.apache.fastandroid.demo.style.FitSystemWindowFrameLayoutDemoActivity
import com.apache.fastandroid.demo.temp.activityresult.ActivityResultDemoActivity
import com.apache.fastandroid.demo.temp.reflect.ReflectionDemoFragment
import com.apache.fastandroid.demo.temp.savestate.SaveStateHandleFragment

/**
 * Created by Jerry on 2021/3/1.
 */
class TempDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("SpannableString", "SpannableString", SpanableStringFragment::class.java)
                ,ViewItemBean("Retention用法", "限定参数为指定类型", RetentionPolicyFragment::class.java)
                ,ViewItemBean("扩大button点击范围", "扩大button点击范围", ExpandBtnClickAreaFragment::class.java)
                ,ViewItemBean("Gson", "Gson", GsonFragment::class.java)
                ,ViewItemBean("枚举", "枚举", EnumFragment::class.java)
                ,ViewItemBean("位或与", "位或与", BitOrAndFragment::class.java)
                ,ViewItemBean("ViewGroup", "shouldDelayChildPressedState", ViewGroupShouldDelayPressStateFragment::class.java)
                ,ViewItemBean("滑动冲突", "滑动冲突外部拦截法", ScrollConflictOuterInterceptDemoFragment::class.java)
                ,ViewItemBean("滑动冲突", "滑动冲突内部拦截法", ScrollConflictInnernterceptDemoFragment::class.java)
                ,ViewItemBean("泛型", "泛型", GenericClassDemoFragment::class.java)
                ,ViewItemBean("RX", "RX操作符", GenericClassDemoFragment::class.java)
                ,ViewItemBean("知识点", "知识点", KnowledgeFragment::class.java)
                ,ViewItemBean("Android 10 作用域存储适配", "Android 10 作用域存储适配", KnowledgeFragment::class.java)
                ,ViewItemBean("指定某个View的父View类型", "指定某个View的父View类型", SpecifyParentViewDemoFragment::class.java)
                ,ViewItemBean("反射", "反射", ReflectionDemoFragment::class.java)
                ,ViewItemBean("Api用法", "Api用法", ApiDemoFragment::class.java)
                ,ViewItemBean("异步转同步", "异步转同步", AsyncToSyncDemoFragment::class.java)
                ,ViewItemBean("死锁", "死锁", ConcurrencyDemoFragment::class.java)
                ,ViewItemBean("RecycleviewSnapHelper", "RecycleviewSnapHelper", SnapHelperDemoFragment::class.java)
                ,ViewItemBean("启动模式", "启动模式", LaunchModeDemoFragment::class.java)
                ,ViewItemBean("FitSystemWindow", "FitSystemWindow", activity = FitSystemWindowDemoActivity::class.java)
                ,ViewItemBean("FitSystemWindow2", "FitSystemWindow2", activity = FitSystemWindowDemoActivity2::class.java)
                ,ViewItemBean("FitSystemWindow3", "FitSystemWindow3", activity = FitSystemWindowFrameLayoutDemoActivity::class.java)
                ,ViewItemBean("LayoutInflater", "LayoutInflater",  LayoutInflaterDemoFragment::class.java)
                ,ViewItemBean("ActivityResultApi", "ActivityResultApi",  activity = ActivityResultDemoActivity::class.java)
                ,ViewItemBean("SavedStateHandle", "SavedStateHandle",  SaveStateHandleFragment::class.java)
                ,ViewItemBean("ActivityStyle", "给Activity设置style",  SaveStateHandleFragment::class.java)

        )
    }
}