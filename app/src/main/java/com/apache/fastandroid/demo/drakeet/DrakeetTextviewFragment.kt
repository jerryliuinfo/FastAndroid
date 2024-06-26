package com.apache.fastandroid.demo.drakeet

import android.os.Bundle
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.drakeet_textview.*

import android.widget.TextView
import androidx.core.widget.NestedScrollView
import com.apache.fastandroid.databinding.DrakeetTextviewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment


/**
 * Created by Jerry on 2021/11/7.
 * https://t.zsxq.com/VfmEiIA
 */
class DrakeetTextviewFragment:BaseBindingFragment<DrakeetTextviewBinding>(DrakeetTextviewBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        btn_bringPointIntoView.setOnClickListener {
            // et_desc.bringPointIntoView(0)
            // et_desc.invalidate()
            bringPointIntoView(et_desc,scrollView,0)
        }


        btn_bringPointIntoView2.setOnClickListener {

            bringPointIntoView(et_desc,scrollView,et_desc.length()/2)

        }
        btn_bringPointIntoView3.setOnClickListener {
            bringPointIntoView(et_desc,scrollView,et_desc.length())
                //android.widget.TextView.bringPointIntoView 测试无效
            // scrollView.requestLayout()
        }
    }

    fun bringPointIntoView(
        textView: TextView,
        scrollView: NestedScrollView, offset: Int,
    ) {
        val line = textView.layout.getLineForOffset(offset)
        val y = ((line + 0.5) * textView.lineHeight).toInt()
        scrollView.smoothScrollTo(0, y - scrollView.height / 2)
    }
}