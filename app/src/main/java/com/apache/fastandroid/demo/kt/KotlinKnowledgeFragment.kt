package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinKnowledgeFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.kt_grammer
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        varargParams()
    }


    private fun varargParams(vararg numbs: Int){
        var list = numbs.toList()
    }
}