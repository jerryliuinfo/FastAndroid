package com.apache.fastandroid.demo.blacktech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_black_tech_aspect.*

/**
 * Created by Jerry on 2021/10/14.
 */
class AspectJDemoFragment:BaseFragment() {
    companion object{
        private const val TAG = "AspectJDemoFragment";
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_black_tech_aspect
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
       /* btn_debounce.setOnClickListener {
            NLog.d(TAG, "onClick --->")

        }*/
        btn_debounce.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                NLog.d(TAG, "onClick --->")
            }

        })
    }
}