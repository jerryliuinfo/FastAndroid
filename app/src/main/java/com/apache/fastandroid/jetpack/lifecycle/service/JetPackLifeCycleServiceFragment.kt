package com.apache.fastandroid.jetpack.lifecycle.service

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.fragment_jetpack_lifecycle_service.*

/**
 * Created by Jerry on 2020/10/31.
 */
class JetPackLifeCycleServiceFragment: BaseStatusFragmentNew() {

    override fun inflateContentView(): Int {
       return R.layout.fragment_jetpack_lifecycle_service
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn_start_service.setOnClickListener {
            val intent = Intent(this@JetPackLifeCycleServiceFragment.activity, MyService::class.java)
            this@JetPackLifeCycleServiceFragment.activity!!.startService(intent)
        }

        btn_stop_service.setOnClickListener {
            val intent = Intent(this@JetPackLifeCycleServiceFragment.activity, MyService::class.java)
            this@JetPackLifeCycleServiceFragment.activity!!.stopService(intent)
        }
    }


}