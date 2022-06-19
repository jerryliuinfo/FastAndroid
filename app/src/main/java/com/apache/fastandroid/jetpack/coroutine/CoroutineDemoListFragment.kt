package com.apache.fastandroid.jetpack.coroutine

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.kt.coroutine.*

/**
 * Created by Jerry on 2020/10/31.
 */
class CoroutineDemoListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("协程","协程", CoroutineDemoFragment::class.java)
            ,ViewItemBean("协程基础","协程基础", CoroutineBasicDemoFragment::class.java)
            ,ViewItemBean("协程取消","协程取消", CoroutineCancelDemoFragment::class.java)
            ,ViewItemBean("协程取消与超时","协程取消与超时", CoroutineCancelTimeoutDemoFragment::class.java)
            ,ViewItemBean("组合挂起函数","组合挂起函数", CoroutineSuspendFuncDemoFragment::class.java)
            ,ViewItemBean("协程上下文与调度器","协程上下文与调度器", CoroutineContextDispatcherDemoFragment::class.java)
            ,ViewItemBean("协程异常处理","协程异常处理", CoroutineExceptionDemoFragment::class.java)
            ,ViewItemBean("BennyHuo","BennyHuo", BennyHuoCoroutineDemoFragment::class.java)
            ,ViewItemBean("携程与Retrofit结合","携程与Retrofit结合最佳实践", CoroutineRetrofitDemoFragment::class.java)
            ,ViewItemBean("携程与RxJava结合","携程与RxJava结合", CoroutineRxjavaDemoFragment::class.java)
        )
    }


}