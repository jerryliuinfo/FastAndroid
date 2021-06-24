package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TouchDelegate
import androidx.coordinatorlayout.widget.ViewGroupUtils
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.expand_btn_click_area.*

/**
 * Created by Jerry on 2021/3/1.
 */
class ExpandBtnClickAreaFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.expand_btn_click_area
    }

    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        btn1.setOnClickListener {
            ToastUtils.showShort("onClick 1111")
        }

        btn1.post {
            val deleateArea = Rect()
            btn1.getHitRect(deleateArea)
            //给btn的点击区域四周都扩大100
            deleateArea.inset(-100,-100)
            ll_parent.touchDelegate = TouchDelegate(deleateArea, btn1)
        }


        btn2.setOnClickListener {
            ToastUtils.showShort("onClick 2222")
        }
        var expandTouchDelegate = ExpandTouchDelegateKt(btn2)

        expandTouchDelegate.expandArea(-100,-100)
    }



}