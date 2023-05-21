package com.apache.fastandroid.demo.transition

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isVisible
import com.apache.fastandroid.databinding.FragmentTransitionBinding
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.tesla.framework.component.imageloader.ImageLoaderManager
import com.tesla.framework.component.imageloader.showImage
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/11/10.
 */
class TransitionDemoFragment: BaseBindingFragment<FragmentTransitionBinding>(FragmentTransitionBinding::inflate) {



    private lateinit var largeImage:ImageView

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        val thumbnail = mBinding.thumbnail
        val layout = thumbnail.parent as ConstraintLayout
        thumbnail.showImage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwx3.sinaimg.cn%2Fmw690%2Fc17fd038ly1gw39cvqaooj20u0190n1l.jpg&refer=http%3A%2F%2Fwx3.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1639105758&t=8ca673f7f8002024d1234bf9e6a978a4")
        thumbnail.setOnClickListener {
            largeImage = ImageView(context)
            thumbnail.isVisible = false

            //注意这里导包要导 android.transition.TransitionManager 而不是 androidX.transition.TransitionManager
            TransitionManager.beginDelayedTransition(layout, MaterialContainerTransform().apply {
                startView = thumbnail
                endView = largeImage
                duration = 10_000
                interpolator = AccelerateDecelerateInterpolator()
                pathMotion = MaterialArcMotion()
                scrimColor = Color.TRANSPARENT
            })

            layout.addView(largeImage, ConstraintLayout.LayoutParams(1066,706).apply {
                topToTop = ConstraintSet.TOP
                bottomToBottom = ConstraintSet.BOTTOM
            })

            largeImage.setOnClickListener {
                TransitionManager.beginDelayedTransition(layout, MaterialContainerTransform().apply {
                    startView = largeImage
                    endView = thumbnail
                    duration = 10_000
                    interpolator = AccelerateDecelerateInterpolator()
                    pathMotion = MaterialArcMotion()
                    scrimColor = Color.TRANSPARENT
                })
                layout.removeView(largeImage)
                thumbnail.isVisible = true
            }
            largeImage.isVisible = true



        }


    }
}