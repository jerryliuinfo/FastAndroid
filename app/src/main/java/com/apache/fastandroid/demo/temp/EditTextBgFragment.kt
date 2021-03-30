package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_edittext_bg.*

/**
 * Created by Jerry on 2021/3/1.
 */
class EditTextBgFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_edittext_bg
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        layout_verify_code.setRootBackground(R.drawable.whtie_gray_corner_bg)
    }
}