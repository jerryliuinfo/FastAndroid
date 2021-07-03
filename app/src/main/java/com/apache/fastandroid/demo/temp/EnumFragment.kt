package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.apache.fastandroid.bean.VersionResponseBean
import com.chad.baserecyclerviewadapterhelper.entity.Person
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.gson_demo.*

/**
 * Created by Jerry on 2021/3/1.
 */
class EnumFragment: BaseFragment() {
    companion object{
        val TAG = "EnumFragment"
    }
    override fun inflateContentView(): Int {
        return R.layout.gson_demo
    }

    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        var recoverTopMode = SilentMode.RECOVER_TOP_ACTIVITY

        NLog.d(TAG, "name: %s", recoverTopMode.name)
    }




}