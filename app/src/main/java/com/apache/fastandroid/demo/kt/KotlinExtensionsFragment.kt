package com.apache.fastandroid.demo.kt

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.doOnDetach
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import com.apache.fastandroid.BuildConfig
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.KtExtensionsBinding
import com.apache.fastandroid.databinding.KtGrammerBinding
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.demo.kt.bean.*
import com.apache.fastandroid.demo.kt.delegate.DelegateList
import com.apache.fastandroid.demo.kt.delegate.People
import com.apache.fastandroid.demo.kt.extensions.GreenLeafyPlant
import com.apache.fastandroid.demo.kt.extensions.print
import com.apache.fastandroid.demo.kt.extensions.pull
import com.apache.fastandroid.demo.kt.inline.PreferenceManager
import com.apache.fastandroid.demo.kt.inline.onlyIf
import com.apache.fastandroid.demo.kt.inline.onlyIf2
import com.apache.fastandroid.demo.kt.operatoroverload.*
import com.apache.fastandroid.demo.kt.refied.RefiedDemo
import com.apache.fastandroid.demo.kt.sealed.*
import com.apache.fastandroid.network.model.Repo
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.state.AquariumPlant
import com.apache.fastandroid.util.DateUtil
import com.blankj.utilcode.util.ResourceUtils
import com.kingja.loadsir.core.LoadSir
import com.microsoft.office.outlook.magnifierlib.frame.FrameCalculator
import com.tesla.framework.common.util.HideTextWatcher
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.maxCustomize
import com.tesla.framework.kt.plusAssign
import com.tesla.framework.ui.fragment.BaseVMFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File
import java.nio.charset.Charset
import kotlin.coroutines.Continuation
import kotlin.math.cos
import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinExtensionsFragment:BaseVMFragment<KtExtensionsBinding>(KtExtensionsBinding::inflate) {
    companion object{
        private const val TAG = "KotlinExtensionsFragment"
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnAnimatorKt.setOnClickListener {
            animatorKt()
        }
        viewBinding.btnDrawableKt.setOnClickListener {
            drawableKt()
        }
        viewBinding.btnExtends.setOnClickListener {
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



    private fun drawableKt() {
        val drawable = ColorDrawable(requireContext().getColor(R.color.comm_red)).apply {
            bounds = Rect(100,100,400,400)
        }
        var bitmap = drawable.toBitmap(200,200)
        val imageView:ImageView = viewBinding.ivAvator
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
        viewBinding.ivAvator.also {
            it.doOnPreDraw {  }
            it.doOnLayout {  }
            it.doOnDetach {  }
        }

    }
}