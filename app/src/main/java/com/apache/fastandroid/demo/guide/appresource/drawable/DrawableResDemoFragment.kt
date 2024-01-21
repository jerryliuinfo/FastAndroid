package com.apache.fastandroid.demo.guide.appresource.drawable

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LevelListDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDrawableBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.getColor
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_drawable.view.btn_level_drawable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2023/12/17.
 * https://developer.android.com/guide/topics/resources/drawable-resource?hl=zh-cn
 */
class DrawableResDemoFragment:BaseBindingFragment<FragmentDrawableBinding>(FragmentDrawableBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnLayerDrawable.setOnClickListener {
            layerDrawableUsage()
        }

        mBinding.btnStatelistDrawable.setOnClickListener {
            stateListDrawableUsage()
        }

        mBinding.btnStatelistDrawableDynamic.setOnClickListener {
            stateListDrawableByCode()
        }

        mBinding.btnLevelDrawable.setOnClickListener {
            levelDrawableUsage()
        }


    }

    private fun levelDrawableUsage() {

        mBinding.imageView.apply {
            setBackgroundResource(R.drawable.level_circle)
            setImageLevel(0)
        }
        val ld = mBinding.imageView.drawable as LevelListDrawable

        lifecycleScope.launch {
            while (true){
                if (ld.level > 10000){
                    ld.level = 0
                }
                mBinding.imageView.setImageLevel(ld.level + 2000)
                delay(200)
            }
        }

    }

    private fun layerDrawableUsage() {
        mBinding.targetView.setBackgroundResource(R.drawable.drawable_layer_list)

    }

    private fun stateListDrawableUsage() {
        mBinding.targetView.setBackgroundResource(R.drawable.drawable_state_lsit)

    }

    private fun stateListDrawableByCode() {
        val stalistDrawable =  StateListDrawable()
        //获取对应的属性值 Android框架自带的属性 attr
        val pressed = android.R.attr.state_pressed
        val focused = android.R.attr.state_focused

        // stalistDrawable.addState(intArrayOf(-pressed), ColorDrawable(R.color.comm_red.getColor(requireContext())))
        stalistDrawable.addState(intArrayOf(pressed), ColorDrawable(R.color.comm_blue.getColor(requireContext())))
        stalistDrawable.addState(intArrayOf(focused), ColorDrawable(R.color.comm_blue.getColor(requireContext())))
        stalistDrawable.addState(intArrayOf(), ColorDrawable(R.color.comm_red.getColor(requireContext())))
        mBinding.targetView.background = stalistDrawable

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.example_menu, menu)
    }


    fun onGroupItemClick(item: MenuItem) {
       Logger.d("onGroupItemClick item:${item.title}")
    }
}