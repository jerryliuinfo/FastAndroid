package com.apache.fastandroid.demo.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.CustomViewClipchildBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.clip_to_padding_layout_item.view.*
import kotlinx.android.synthetic.main.custom_view_clip_to_padding.*

/**
 * Created by Jerry on 2020/12/2.
 */
class ClipToPaddingFragment :
    BaseBindingFragment<CustomViewClipchildBinding>(CustomViewClipchildBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


    }


}