package com.apache.fastandroid.jetpack.livedataviewmodel

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_viewmodel.*

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataViewModelFragment:ABaseFragment() {
    private val nameViewModel by lazy {
        NameViewModel()
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_viewmodel
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        nameViewModel.nameLiveData.observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                text_name.text = t
            }

        })
        btn_change.setOnClickListener {
            nameViewModel.nameLiveData.value = "hello"
        }
    }


}