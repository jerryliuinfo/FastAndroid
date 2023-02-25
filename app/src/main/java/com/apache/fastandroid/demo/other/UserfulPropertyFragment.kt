package com.apache.fastandroid.demo.other

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.CustomViewClipchildBinding
import com.apache.fastandroid.databinding.CustomViewUserfullPropertyBinding
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.clip_to_padding_layout_item.view.*
import kotlinx.android.synthetic.main.custom_view_clip_to_padding.*

/**
 * Created by Jerry on 2020/12/2.
 */
class UserfulPropertyFragment :
    BaseBindingFragment<CustomViewUserfullPropertyBinding>(CustomViewUserfullPropertyBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnDigist.addTextChangedListener(afterTextChanged = {text: Editable? ->

            Logger.d("afterTextChanged text:${text.toString()}")

        })

        mBinding.layoutDuplicateParentState.setOnClickListener {
            Logger.d("查看子 View 是否有点击效果")

        }

        mBinding.btnDuplicateParentState.setOnClickListener {
            Logger.d("按钮被点击了")
        }

    }





}