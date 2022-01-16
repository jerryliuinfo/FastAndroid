package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.apache.fastandroid.LogUtils
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.fragment_jetpack_livedata.*

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataStickEventFragment : BaseStatusFragmentNew(){
    companion object{
        val TAG = "LiveDataBasicFragment"
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_jetpack_livedata_stick_event
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        val nameLiveData = MutableLiveData<String>()
        //先发送事件，再注册观察，也能收到观察之前发送的数据
        nameLiveData.value = "Jerry";

        nameLiveData.observe(this, Observer<String> { t ->
            LogUtils.d("onChanged : $t")
            text_name.text = t
        })

    }

}