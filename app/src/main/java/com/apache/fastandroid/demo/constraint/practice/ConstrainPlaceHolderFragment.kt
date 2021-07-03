package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.constraint_place_holder.*

/**
 * Placeholder 作用是用来占位，它可以在布局中占好位置，通过app:content 属性或者动态调用 setContent() 设置内容，来让某个控件
 * 移动到此占位符中
 */
class ConstrainPlaceHolderFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_place_holder
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        favorite.setOnClickListener {
            onClickView(it)
        }
        mail.setOnClickListener {
            onClickView(it)
        }
        save.setOnClickListener {
            onClickView(it)
        }
        play.setOnClickListener {
            onClickView(it)
        }
    }

    private fun onClickView(view:View){
        placeholder.setContentId(view.id)
    }
}