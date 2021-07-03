package com.apache.fastandroid.jetpack.lifecycle.traditional

import android.os.Bundle
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentTraditionalLifecycleBinding
import com.apache.fastandroid.jetpack.GpsEngine
import com.tesla.framework.ui.fragment.BaseTraceFragment

/**
 * Created by Jerry on 2020/11/1.
 */
class TraditionalLifeCycleFragment: BaseTraceFragment<FragmentTraditionalLifecycleBinding>() {

    override fun inflateContentView(): Int {
        return R.layout.fragment_traditional_lifecycle
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        GpsEngine.getInstance().onResumeAction()
    }

    override fun onPause() {
        super.onPause()
        GpsEngine.getInstance().onPauseAction()
    }
}