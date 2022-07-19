package com.apache.fastandroid.demo.kt

import android.animation.ObjectAnimator
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.doOnDetach
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.Person
import com.apache.fastandroid.databinding.KtExtensionsBinding
import com.apache.fastandroid.demo.kt.extensions.GreenLeafyPlant
import com.apache.fastandroid.demo.kt.extensions.print
import com.apache.fastandroid.demo.kt.extensions.pull
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.state.AquariumPlant
import com.apache.fastandroid.state.UserInfo
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinExtensionsFragment:BaseVBFragment<KtExtensionsBinding>(KtExtensionsBinding::inflate) {
    companion object{
        private const val TAG = "KotlinExtensionsFragment"
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnAnimatorKt.setOnClickListener {
            animatorKt()
        }
        mBinding.btnDrawableKt.setOnClickListener {
            drawableKt()
        }
        mBinding.btnExtends.setOnClickListener {
            extensionOverload()
        }
    }

    private fun extensionOverload(){
        val plant = GreenLeafyPlant(10)
        //print GreenLeafyPlant
        plant.print()
        val acquirePlant: AquariumPlant = plant
        //print AquariumPlant
        acquirePlant.print()

        //可空接收器
        val plaint2:AquariumPlant ?= null
        plaint2.pull()
    }


    fun Person?.isAdult():Boolean{
        return this?.age?.let {
            it > 10
        } ?: false

    }

    private fun drawableKt() {
        val drawable = ColorDrawable(requireContext().getColor(R.color.comm_red)).apply {
            bounds = Rect(100,100,400,400)
        }
        var bitmap = drawable.toBitmap(200,200)
        val imageView:ImageView = mBinding.ivAvator
        imageView.setImageBitmap(bitmap)


    }

    private fun animatorKt() {
        val animator = ObjectAnimator.ofFloat(0f,1f)
        animator.doOnStart {
            println("doOnStart")
        }
        animator.doOnEnd {
            println("doOnEnd")
        }
        animator.start()

    }

    private fun viewKtExtension() {
        mBinding.ivAvator.also {
            it.doOnPreDraw {  }
            it.doOnLayout {  }
            it.doOnDetach {  }
        }

    }
}