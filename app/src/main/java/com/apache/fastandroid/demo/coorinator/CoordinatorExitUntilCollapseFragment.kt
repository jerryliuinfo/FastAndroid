package com.apache.fastandroid.demo.coorinator

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.CoordinatorScrollflagsEnteralwaysBinding
import com.apache.fastandroid.databinding.CoordinatorScrollflagsExituntilCollapseBinding


import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2020/11/19.
 *
 * 当您向上滚动时，视图(工具栏)将可见，要查看视图的扩展版本(具有 ImageView 和其他视图) ，
 * 您需要滚动到页面顶部。当你向下滚动时，正常的滚动就会像其他的 ScrollView 一样出现。
 */
class CoordinatorExitUntilCollapseFragment :
    BaseVBFragment<CoordinatorScrollflagsExituntilCollapseBinding>(CoordinatorScrollflagsExituntilCollapseBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


    }


}