package com.apache.fastandroid.demo

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.apache.fastandroid.demo.network.InjectorUtil
import com.apache.fastandroid.demo.network.PrivacyViewModelNew
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.framgent_retrofit.*

/**
 * Created by Jerry on 2021/1/20.
 */
class RetrofitDemoFragment:BaseFragment() {
    private val viewModel by lazy { ViewModelProviders.of(this, InjectorUtil.getWeatherModelFactory()).get(PrivacyViewModelNew::class.java) }

    override fun inflateContentView(): Int {
        return R.layout.framgent_retrofit
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn_click.setOnClickListener {
            viewModel.getAgreementData()
        }
        viewModel.privacy.observe(this, Observer {
            NLog.d(TAG, "data: %s", it)
        })
    }
}