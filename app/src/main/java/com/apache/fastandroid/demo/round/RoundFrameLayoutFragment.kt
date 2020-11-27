package com.apache.fastandroid.demo.round

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.bumptech.glide.Glide
import com.tesla.framework.ui.fragment.ABaseFragment

/**
 * Created by Jerry on 2020/11/19.
 */
class RoundFrameLayoutFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.custom_view_round_framelayout
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

    }
}