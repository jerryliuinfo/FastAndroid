package com.apache.fastandroid.demo.blacktech.easytrack

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew

/**
 * Created by Jerry on 2022/1/15.
 */
class EasyTrackDemoFragment:BaseStatusFragmentNew() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_blacktrack_easytrack
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


    }
}