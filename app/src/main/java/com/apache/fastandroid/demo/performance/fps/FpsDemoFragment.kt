package com.apache.fastandroid.demo.performance.fps

import com.apache.fastandroid.databinding.FragmentTaktBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import jp.wasabeef.takt.Takt

/**
 * Created by Jerry on 2022/6/9.
 */
class FpsDemoFragment:BaseVBFragment<FragmentTaktBinding>(FragmentTaktBinding::inflate) {

    override fun onStart() {
        super.onStart()
        Takt.play()
    }

    override fun onStop() {
        super.onStop()
    }
}