package com.apache.fastandroid.demo.rxjava

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.ui.fragment.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.SchedulerSupport.IO
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_rxjava.*


/**
 * Created by Jerry on 2021/9/9.
 */
class RxJavaDemoFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_rxjava
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_basic_usage.setOnClickListener {
            ApiEngine.createApiService().loadTopArticleCo2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                                var data = it.data
                }, {
                    ToastUtils.showShort(it.message)
                })
        }
    }
}