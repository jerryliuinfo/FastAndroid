package com.apache.fastandroid.demo.temp

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment

/**
 * Created by Jerry on 2021/3/1.
 */
class TempDemoFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("SpannableString", "SpannableString", SpanableStringFragment::class.java)
                ,ViewItemBean("EditText背景", "EditText背景", EditTextBgFragment::class.java)
                ,ViewItemBean("Retention用法", "限定参数为指定类型", RetentionPolicyFragment::class.java)

        )
    }
}