package com.apache.fastandroid.jetpack.lifecycle.traditional

import android.os.Bundle
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment

/**
 * Created by Jerry on 2020/11/1.
 */
class TraditionalLifeCycleFragment: BaseFragment() {

    private lateinit var lifeCycleListener: TraditionalLifeCycleListener
    override fun inflateContentView(): Int {
        return R.layout.fragment_traditional_lifecycle
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycleListener = TraditionalLifeCycleListener()
        lifeCycleListener.onCreate2(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        // 必须要手动调用lifeCycleListener.onDestroy()，LifeCycleListener才会知道Fragment已经被销毁了
        lifeCycleListener.onDestroy(this)
    }

}