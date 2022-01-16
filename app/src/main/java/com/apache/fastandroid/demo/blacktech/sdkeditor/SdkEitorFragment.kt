package com.apache.fastandroid.demo.blacktech.sdkeditor

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.fragment_black_tech_sdk_editor.*

/**
 * Created by Jerry on 2022/1/15.
 */
class SdkEitorFragment:BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_black_tech_sdk_editor
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_sdk_editor.setOnClickListener {
//            LoadSir.getDefault()
        }
    }
}