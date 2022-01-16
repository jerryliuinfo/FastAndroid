package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TouchDelegate
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.expand_btn_click_area.*

/**
 * Created by Jerry on 2021/3/1.
 */
class ExpandBtnClickAreaFragment: BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.expand_btn_click_area
    }

    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        btnOr.setOnClickListener {
            ToastUtils.showShort("onClick 1111")
        }

        btnOr.post {
            val deleateArea = Rect()
            btnOr.getHitRect(deleateArea)
            //给btn的点击区域四周都扩大100
            deleateArea.inset(-100,-100)
            ll_parent.touchDelegate = TouchDelegate(deleateArea, btnOr)
        }


        btn2.setOnClickListener {
            ToastUtils.showShort("onClick 2222")
        }
        var expandTouchDelegate = ExpandTouchDelegateKt(btn2)

        expandTouchDelegate.expandArea(-100,-100)
    }



}