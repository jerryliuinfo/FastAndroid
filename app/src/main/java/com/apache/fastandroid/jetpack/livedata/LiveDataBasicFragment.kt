package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.apache.fastandroid.LogUtils
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.fragment_jetpack_livedata.*

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataBasicFragment : BaseFragment(){
    companion object{
        val TAG = "LiveDataBasicFragment"
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        val nameLiveData = MutableLiveData<String>()
        val booleanLiveData = MutableLiveData<Boolean>()
        nameLiveData.observe(this, Observer<String> { t -> LogUtils.d("onChanged : $t")
            text_name.text = t
        })
        btn_change.setOnClickListener {
            nameLiveData.value = "Zairian"
        }

        btn_read.setOnClickListener {
            NLog.d(TAG, "booleanLiveData: %s", booleanLiveData.value)
            if (booleanLiveData.value != null){
                NLog.d(TAG, "booleanLiveData.value != null")

            }
        }
    }

}