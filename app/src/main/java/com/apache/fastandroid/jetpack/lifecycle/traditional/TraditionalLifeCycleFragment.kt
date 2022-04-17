package com.apache.fastandroid.jetpack.lifecycle.traditional

import android.os.Bundle
import com.apache.fastandroid.databinding.FragmentTraditionalLifecycleBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2020/11/1.
 */
class TraditionalLifeCycleFragment: BaseVBFragment<FragmentTraditionalLifecycleBinding>(FragmentTraditionalLifecycleBinding::inflate) {

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