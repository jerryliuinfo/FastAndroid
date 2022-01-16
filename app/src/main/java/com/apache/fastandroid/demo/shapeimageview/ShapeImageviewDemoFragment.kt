package com.apache.fastandroid.demo.shapeimageview

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 * Created by Jerry on 2021/8/31.
 */
class ShapeImageviewDemoFragment: BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_shapeimageview
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

    }
}