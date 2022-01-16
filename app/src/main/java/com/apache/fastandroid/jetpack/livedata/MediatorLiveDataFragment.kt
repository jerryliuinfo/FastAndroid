package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.apache.fastandroid.jetpack.reporsity.UserDao
import com.apache.fastandroid.jetpack.reporsity.UserNetwork
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_media_livedata.*

/**
 * Created by Jerry on 2021/2/7.
 * 合并多个 LiveData 源。只要任何原始的 LiveData 源对象发生更改，就会触发 MediatorLiveData 对象的观察者
 */
class MediatorLiveDataFragment: BaseStatusFragmentNew() {
    companion object{
        private const val TAG = "MediatorLiveDataFragment"
    }

    private val userViewModel by lazy {
        UserInfoViewModel(UserReporsity.getInstance(
            UserDao.getInstance(),
            UserNetwork().getInstance()))
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_jetpack_livedata_media_livedata
    }
    val livedata1 = MutableLiveData<String>()
    val livedata2 = MutableLiveData<String>()
    val mediaLiveData = MediatorLiveData<String>()


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        mediaLiveData.addSource(livedata1) {
            NLog.d(TAG, "livedata1 onchange: %s", it)
//            mediaLiveData.value = it
        }
        mediaLiveData.addSource(livedata2){
            NLog.d(TAG, "livedata2 onchange: %s", it)
            mediaLiveData.value = it
        }
        mediaLiveData.observe(this, Observer {
            NLog.d(TAG, "mediaLiveData onChange: %s", it)
            text_name.text = it
        })
//        mediaLiveData.observe(this,observer)

        btn_livedata1.setOnClickListener {
            livedata1.value = "livedata1"
        }
        btn_livedata2.setOnClickListener {
            livedata2.value = "livedata2"
        }
    }

    private val observer = Observer<String> {
        NLog.d(TAG, "observer onchange: %s", it)
        mediaLiveData.value = it
    }
}