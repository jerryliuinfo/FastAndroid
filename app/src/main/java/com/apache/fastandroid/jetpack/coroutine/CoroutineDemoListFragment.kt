package com.apache.fastandroid.jetpack.coroutine

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.kt.coroutine.BennyHuoCoroutineDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineBasicDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineCancelDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineCancelTimeoutDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineContextDispatcherDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineExceptionDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineNetworkDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineRetrofitDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineRxjavaDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineShowCaseFragment
import com.apache.fastandroid.demo.kt.coroutine.CoroutineSuspendFuncDemoFragment
import com.apache.fastandroid.demo.kt.coroutine.rengwuxian.CoroutineRengwuXianDemoFragment

/**
 * Created by Jerry on 2020/10/31.
 * done
 */
class CoroutineDemoListFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("扔物线协程","扔物线协程", CoroutineRengwuXianDemoFragment::class.java),

            ViewItemBean("扔物线协程","扔物线协程公开课", CoroutineShowCaseFragment::class.java),


            ViewItemBean("协程基础","协程基础", CoroutineBasicDemoFragment::class.java),

            ViewItemBean("协程取消","协程取消", CoroutineCancelDemoFragment::class.java),
            ViewItemBean("协程取消与超时","协程取消与超时", CoroutineCancelTimeoutDemoFragment::class.java),
            ViewItemBean("组合挂起函数","组合挂起函数", CoroutineSuspendFuncDemoFragment::class.java),
            ViewItemBean("协程上下文与调度器","协程上下文与调度器", CoroutineContextDispatcherDemoFragment::class.java),
            ViewItemBean("协程异常处理","协程异常处理", CoroutineExceptionDemoFragment::class.java),
            ViewItemBean("BennyHuo","BennyHuo", BennyHuoCoroutineDemoFragment::class.java),
            ViewItemBean("携程与Retrofit结合","携程与Retrofit结合最佳实践", CoroutineRetrofitDemoFragment::class.java),
            ViewItemBean("携程与RxJava结合","携程与RxJava结合", CoroutineRxjavaDemoFragment::class.java),
            ViewItemBean("携程做网络请求","携程做网络请求", CoroutineNetworkDemoFragment::class.java)
        )
    }


}