package com.apache.fastandroid.demo.drakeet.common

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.jetpack.lifecycle.service.MyService
import com.tesla.framework.common.util.AndroidVersion
import com.tesla.framework.common.util.toast.ToastCompat
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_drakeet_knowledge.*

/**
 * Created by Jerry on 2021/10/15.
 */
class DrakeetCommonFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_drakeet_knowledge
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        //https://wx.zsxq.com/dweb2/index/topic_detail/581111245444844

        btn_toast.setOnClickListener {
            ToastCompat.makeText(context, "i am toast",ToastCompat.LENGTH_SHORT).show()
        }
        btn_judge_sdk_version.setOnClickListener {
            AndroidVersion.isAndroid12()
        }

        btn_service_ontask_removed.setOnClickListener {
            context?.let { it1 -> MyService.start(it1) }
        }

    }

}