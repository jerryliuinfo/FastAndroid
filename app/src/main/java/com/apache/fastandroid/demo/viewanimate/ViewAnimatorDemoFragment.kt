package com.apache.fastandroid.demo.viewanimate

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentViewAnimatorActivityMainBinding
import com.tesla.framework.component.animate.AnimationListener
import com.tesla.framework.component.animate.ViewAnimator
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.util.Locale

/**
 * Created by Jerry on 2023/8/29.
 */
open class ViewAnimatorDemoFragment : BaseBindingFragment<FragmentViewAnimatorActivityMainBinding>(
    FragmentViewAnimatorActivityMainBinding::inflate
) {
    var image: ImageView? = null
    var mountain: ImageView? = null
    var text: TextView? = null
    var percent: TextView? = null

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        image = findViewById<View>(R.id.image) as ImageView
        mountain = findViewById<View>(R.id.mountain) as ImageView
        text = findViewById<View>(R.id.text) as TextView
        percent = findViewById<View>(R.id.percent) as TextView

        findViewById<View>(R.id.parallel).setOnClickListener { animateParallel() }

        findViewById<View>(R.id.mountain).setOnClickListener { simpleAnimation() }

        findViewById<View>(R.id.sequentially).setOnClickListener { animateSequentially() }

    }

    protected fun simpleAnimation() {
        ViewAnimator.animate(mountain)
            .translationY(-1000f, 0f)
            .alpha(0f, 1f)
            .andAnimate(text)
            .translationX(-200f, 0f)
            .interpolator(DecelerateInterpolator())
            .duration(2000)
            .thenAnimate(mountain)
            .scale(1f, 0.5f, 1f)
            .interpolator(AccelerateInterpolator())
            .duration(1000)
            .start()
    }

    protected fun animateParallel() {
        val viewAnimator = ViewAnimator.animate(mountain, image)
            .dp().translationY(-1000f, 0f)
            .alpha(0f, 1f)
            .singleInterpolator(OvershootInterpolator())
            .andAnimate(percent)
            .scale(0f, 1f)
            .andAnimate(text)
            .textColor(Color.BLACK, Color.WHITE)
            .backgroundColor(Color.WHITE, Color.BLACK)
            .waitForHeight()
            .singleInterpolator(AccelerateDecelerateInterpolator())
            .duration(2000)
            .thenAnimate(percent)
            .custom(object : AnimationListener.Update<TextView> {

                override fun update(view: TextView?, value: Float) {
                    view?.text = String.format(Locale.US, "%.02f%%", value)

                }
            }, 0f, 1f)
            .andAnimate(image)
            .rotation(0f, 360f)
            .duration(5000)
            .start()
        Handler(Looper.getMainLooper()).postDelayed({
            viewAnimator.cancel()
            Toast.makeText(requireContext(), "animator canceled", Toast.LENGTH_SHORT).show()
        }, 4000)
    }

    protected fun animateSequentially() {
        ViewAnimator.animate(image)
            .dp().width(100f, 150f)
            .alpha(1f, 0.1f)
            .interpolator(DecelerateInterpolator())
            .duration(800)
            .thenAnimate(image)
            .dp().width(150f, 100f)
            .alpha(0.1f, 1f)
            .interpolator(AccelerateInterpolator())
            .duration(1200)
            .start()
    }
}