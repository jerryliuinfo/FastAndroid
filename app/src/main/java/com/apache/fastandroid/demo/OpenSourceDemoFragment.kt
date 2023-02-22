package com.apache.fastandroid.demo

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.agentweb.AgentWebDemoListFragment
import com.apache.fastandroid.demo.app.MvvmMailDemoFragment
import com.apache.fastandroid.demo.bestpay.BestPayDemoFragment
import com.apache.fastandroid.demo.component.bundle.BundleDemoActivity
import com.apache.fastandroid.demo.component.loadsir.LoadSirDemoListFragment
import com.apache.fastandroid.demo.component.once.OnceFragment
import com.apache.fastandroid.demo.glide.GlideDemoFragment
import com.apache.fastandroid.demo.hawk.HawkDemoFragment
import com.apache.fastandroid.demo.logger.LoggerDemoFragment
import com.apache.fastandroid.demo.mmkv.MMKVFragment
import com.apache.fastandroid.demo.mmkv.MMKVKtxFragment
import com.apache.fastandroid.demo.mvi.MviDemoFragment
import com.apache.fastandroid.demo.rxjava.RxJavaDemoListFragment
import com.apache.fastandroid.demo.searchPreference.SearchPreferenceDemoListFragment
import com.apache.fastandroid.demo.skydoves.SandWitchDemoFragment

/**
 * Created by Jerry on 2020/11/11.
 */
class OpenSourceDemoFragment: BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("Glide", "Glide", GlideDemoFragment::class.java)
                ,ViewItemBean("Rxjava", "Rxjava操作符", RxJavaDemoListFragment::class.java)
                ,ViewItemBean("AgentWeb", "AgentWeb", AgentWebDemoListFragment::class.java)
                ,ViewItemBean("AppMananger", "MuntashirAkon/AppManager", AgentWebDemoListFragment::class.java)
                ,ViewItemBean("PermissionX", "PermissionX", PermissionXFragment::class.java)
                ,ViewItemBean("LoadSir", "LoadSir", LoadSirDemoListFragment::class.java)
                ,ViewItemBean("logger", "LoadSir", LoggerDemoFragment::class.java)
                ,ViewItemBean("BestPay", "BestPay", BestPayDemoFragment::class.java)
                ,ViewItemBean("Hawk", "Hawk", HawkDemoFragment::class.java)
                ,ViewItemBean("MMKV", "MMKV", MMKVFragment::class.java)
                ,ViewItemBean("MMKV KTX", "MMKV KTX", MMKVKtxFragment::class.java)
                ,ViewItemBean("UtilCodex", "UtilCodex", MMKVKtxFragment::class.java)
                    //MVVM Mail
                ,ViewItemBean("Mvvm Mail", "Mvvm Mail", MvvmMailDemoFragment::class.java)
                ,ViewItemBean("MVI模式", "shenzhen2017/android-architecture", MviDemoFragment::class.java)
                ,ViewItemBean("sandwitch", "sandwitch", SandWitchDemoFragment::class.java)
                ,ViewItemBean("Bundle", "Bundle", SandWitchDemoFragment::class.java)
                ,ViewItemBean("Once", "Once", OnceFragment::class.java)
                ,ViewItemBean("material-dialogs", "material-dialogs", OnceFragment::class.java)
                ,ViewItemBean("bundler", "https://github.com/skydoves/Bundler", activity=BundleDemoActivity::class.java)
                ,ViewItemBean("SearchPreference", "https://github.com/ByteHamster/SearchPreference", SearchPreferenceDemoListFragment::class.java)

        )
    }


}