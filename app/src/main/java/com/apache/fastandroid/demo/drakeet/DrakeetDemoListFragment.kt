package com.apache.fastandroid.demo.drakeet

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.drakket.hookcontext.HookContextFragment
import com.apache.fastandroid.demo.drakeet.common.DrakeetCommonFragment
import com.apache.fastandroid.demo.drakeet.foregroundservice.ForegroundServiceFragment

/**
 * Created by Jerry on 2021/9/8.
 */
class DrakeetDemoListFragment:BaseListFragment() {
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
             ViewItemBean("HookContext", "HookContext", HookContextFragment::class.java)
            ,ViewItemBean("DrakeetCommon", "DrakeetCommon", DrakeetCommonFragment::class.java)
            ,ViewItemBean("EdgeEffect", "EdgeEffect", DrakeetEdgeEffectDemoFragment::class.java)
            ,ViewItemBean("动态newView时设置style", "动态newView时设置style", ProgrammaticalySetStyleFragment::class.java)
            ,ViewItemBean("接管Lopper", "接管Lopper", LooperFragment::class.java)
            ,ViewItemBean("ForeGroundService", "ForeGroundService", ForegroundServiceFragment::class.java)
            ,ViewItemBean("RecycleView#setHasStableIds", "setHasStableIds", RecycleviewStabledIdsFragment::class.java)
            ,ViewItemBean("加载大图", "ImageRegionDecoder", RegionDeocderFragment::class.java)
            ,ViewItemBean("ServiceOnTaskRemoved", "ServiceOnTaskRemoved", RegionDeocderFragment::class.java)
            ,ViewItemBean("代码保护、隐藏", "ServiceOnTaskRemoved", RegionDeocderFragment::class.java)
            ,ViewItemBean("Textview偏僻方法", "Textview偏僻方法", DrakeetTextviewFragment::class.java)

        )
    }


}