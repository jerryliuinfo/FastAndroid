package com.apache.fastandroid.demo.performance.frameanimation

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentFrameAnimationBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/5/9.
 */
class FrameAnimationDemoFragment:BaseBindingFragment<FragmentFrameAnimationBinding>(FragmentFrameAnimationBinding::inflate) {

    private var mAnim:AnimationDrawable ?= null

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mAnim = mBinding.animationView.background as AnimationDrawable?



        mBinding.btnStart.setOnClickListener {
            mAnim?.start()
        }

        mBinding.btnEnd.setOnClickListener {
            mAnim?.stop()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mAnim?.stop()
        mBinding.animationView.background = null
    }


}