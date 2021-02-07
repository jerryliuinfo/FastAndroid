package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.apache.fastandroid.LogUtils
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_jetpack_livedata.*

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataBasicFragment :BaseFragment(){
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        val nameLiveData = MutableLiveData<String>()
        nameLiveData.observe(this, Observer<String> { t -> LogUtils.d("onChanged : $t")
            text_name.text = t
        })
        btn_change.setOnClickListener {
            nameLiveData.value = "Zairian"
        }
    }

}