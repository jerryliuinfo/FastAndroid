package com.apache.fastandroid.demo.widget.qmui

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.qmui_demo.*

/**
 * Created by Jerry on 2021/5/4.
 */
class QMUIDemoFragment: BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.qmui_demo
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        btn_login.setChangeAlphaWhenDisable(true)
        btn_login.setChangeAlphaWhenPress(true)
        btn_diable.setOnClickListener {
            btn_login.isEnabled = !btn_login.isEnabled

        }
        btn_click.setOnClickListener {
            btn_login.isPressed = !btn_login.isPressed
        }
    }
}