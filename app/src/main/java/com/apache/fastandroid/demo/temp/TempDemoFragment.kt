package com.apache.fastandroid.demo.temp

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.drakket.hookcontext.HookContextFragment

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
                ,ViewItemBean("HookContext", "HookContext", HookContextFragment::class.java)

        )
    }
}