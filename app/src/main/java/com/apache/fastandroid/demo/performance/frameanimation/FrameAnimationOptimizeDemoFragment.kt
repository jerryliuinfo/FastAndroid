package com.apache.fastandroid.demo.performance.frameanimation

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.apache.fastandroid.databinding.FragmentFrameAnimationBinding
import com.apache.fastandroid.databinding.FragmentFrameAnimationOptimizeBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import com.yuyashuai.frameanimation.FrameAnimation
import com.yuyashuai.frameanimation.FrameAnimationView

/**
 * Created by Jerry on 2023/5/9.
 */
class FrameAnimationOptimizeDemoFragment:BaseBindingFragment<FragmentFrameAnimationOptimizeBinding>(FragmentFrameAnimationOptimizeBinding::inflate) {


    private lateinit var mAnimationView: FrameAnimationView

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mAnimationView = mBinding.animationView
        mAnimationView.setRepeatMode(FrameAnimation.RepeatMode.INFINITE)


        mBinding.btnStart.setOnClickListener {
            mAnimationView.playAnimationFromAssets("anim_check")
        }

        mBinding.btnEnd.setOnClickListener {
            mAnimationView.stopAnimation()
        }
    }

    override fun onResume() {
        super.onResume()
        mAnimationView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mAnimationView.onPause()

    }



}