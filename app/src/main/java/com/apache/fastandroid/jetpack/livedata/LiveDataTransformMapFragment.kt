package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.jetpack.reporsity.UserDao
import com.apache.fastandroid.jetpack.reporsity.UserNetwork
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_transform_map.*

/**
 * Created by Jerry on 2021/2/7.
 * 如果希望在将 LiveData 对象分派给观察者之前对存储在其中的值进行更改，或者需要根据另一个实例的值返回不同的 LiveData 实例，可以使用LiveData中提供的Transformations类。
 */
class LiveDataTransformMapFragment: BaseStatusFragmentNew() {
    companion object{
        private const val TAG = "LiveDataTransformMapFragment"
    }

    private val userViewModel by lazy {
        UserInfoViewModel(UserReporsity.getInstance(
            UserDao.getInstance(),
            UserNetwork().getInstance()))
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_livedata_transform_map
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        userViewModel.mUserName.observe(this, androidx.lifecycle.Observer {
            NLog.d(TAG, "userName onChange: %s", it)
            text_name.text = it
        })

        btn_change.setOnClickListener {
            userViewModel.changeValue()
        }
        userViewModel.loading.observe(this,{
            if (it){

            }
        })
    }
}