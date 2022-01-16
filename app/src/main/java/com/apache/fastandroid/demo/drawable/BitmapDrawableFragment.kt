package com.apache.fastandroid.demo.drawable

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.kt.dp
import com.tesla.framework.kt.dpInt
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.drawable_custom.*

/**
 * Created by Jerry on 2021/12/14.
 */
class BitmapDrawableFragment: BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_drawable_bitmap
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


    }
}