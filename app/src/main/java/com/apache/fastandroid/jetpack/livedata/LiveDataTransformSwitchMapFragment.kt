package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.apache.fastandroid.jetpack.reporsity.UserDao
import com.apache.fastandroid.jetpack.reporsity.UserNetwork
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import com.tesla.framework.common.util.SimpleTextWatcher
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_transform_map.text_name
import kotlinx.android.synthetic.main.fragment_jetpack_livedata_transform_switch_map.*

/**
 * Created by Jerry on 2021/2/7.
 */
class LiveDataTransformSwitchMapFragment: BaseStatusFragmentNew() {
    companion object{
        private const val TAG = "LiveDataTransformMapFragment"
    }

    private val userViewModel by lazy {
        UserInfoViewModel(UserReporsity.getInstance(
            UserDao.getInstance(),
            UserNetwork().getInstance()))
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_jetpack_livedata_transform_switch_map
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
       /* userViewModel.postCard.observe(this, androidx.lifecycle.Observer {
            NLog.d(TAG, "postCard onChange: %s", it)
            text_name.text = it
        })*/
        userViewModel.postCardLiveData.observe(this, Observer {
            text_name.text = it

        })



        et_address.addTextChangedListener(object :SimpleTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                userViewModel.loadPostCard(s.toString())
            }
        })
    }
}