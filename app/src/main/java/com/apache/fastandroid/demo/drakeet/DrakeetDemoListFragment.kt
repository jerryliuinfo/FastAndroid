package com.apache.fastandroid.demo.drakeet

import android.os.Bundle
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.drakeet.aboutpage.AboutPageDemoActivity
import com.apache.fastandroid.demo.drakeet.common.DrakeetCommonFragment
import com.apache.fastandroid.demo.drakeet.customgroup.CustomViewGroupFragment
import com.apache.fastandroid.demo.drakeet.foregroundservice.ForegroundServiceFragment
import com.apache.fastandroid.demo.drakeet.multitype.MultiTypeDemoFragment
import com.apache.fastandroid.demo.floo.FlooMainActivity

/**
 * Created by Jerry on 2021/9/8.
 */
class DrakeetDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("MultiType", "MultiType", MultiTypeDemoFragment::class.java),
            ViewItemBean("AboutPage", "AboutPage", activity = AboutPageDemoActivity::class.java),
            ViewItemBean("Floo", "A dynamic URL router", activity = FlooMainActivity::class.java),
            ViewItemBean("DrakeetCommon", "DrakeetCommon", DrakeetCommonFragment::class.java),
            ViewItemBean("EdgeEffect", "EdgeEffect", DrakeetEdgeEffectDemoFragment::class.java),
            ViewItemBean("动态newView时设置style", "动态newView时设置style", ProgrammaticalySetStyleFragment::class.java),
            ViewItemBean("接管Lopper", "接管Lopper", LooperFragment::class.java),
            ViewItemBean("ForeGroundService", "ForeGroundService", ForegroundServiceFragment::class.java),
            ViewItemBean("加载大图", "ImageRegionDecoder", RegionDeocderFragment::class.java),
            ViewItemBean("ServiceOnTaskRemoved", "ServiceOnTaskRemoved", RegionDeocderFragment::class.java),
            ViewItemBean("代码保护、隐藏", "ServiceOnTaskRemoved", RegionDeocderFragment::class.java),
            ViewItemBean("Textview偏僻方法", "Textview偏僻方法", DrakeetTextviewFragment::class.java),
            ViewItemBean("流畅动画", "一启动就执行动画", FluentAnimationDemoFragment::class.java),
            ViewItemBean("自定义ViewGroup", "自定义ViewGroup", CustomViewGroupFragment::class.java),
            ViewItemBean("流畅动画", "主线程空闲时执行动画", FluentAnimationDemoFragment::class.java, args = Bundle().apply {
                putBoolean("doOnIdle",true)
            }))

    }


}