package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.ConstraintLayerBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.constraint_layer.*

/**
 * Layer 相当于把引用的视图圈一起，可以进行设置背景色，padding等
 * 给Layer添加动画，就是给里面的每一个视图添加动画，对于一些复杂的动画的场景还是比较有用的
 * 这里我们讲A，B，C添加到一个Layer, 同时设置背景色和padding
 */
class ConstraintLayerFragment: BaseBindingFragment<ConstraintLayerBinding>(ConstraintLayerBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        title.setOnClickListener{
            layer.apply {
                rotation = 45f
                translationX = 100f
                translationY = 100f
            }

        }
    }

}