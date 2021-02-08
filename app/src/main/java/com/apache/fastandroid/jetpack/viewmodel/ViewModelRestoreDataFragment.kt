package com.apache.fastandroid.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.lifecycle.Observer
import com.apache.fastandroid.R
import com.apache.fastandroid.demo.bean.user.UserBean
import com.apache.fastandroid.jetpack.livedata.PostCardReporsity
import com.tesla.framework.common.util.log.FastLog
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_jetpack_viewmodel_restore_data.*

/**
 * Created by Jerry on 2020/11/1.
 */
class ViewModelRestoreDataFragment:ABaseFragment() {
    private val userInfoViewModel :UserInfoViewModel by lazy {
        UserInfoViewModel(PostCardReporsity.get())
    }

    override fun inflateContentView(): Int {
       return R.layout.fragment_jetpack_viewmodel_restore_data
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        tv_msg.text = userInfoViewModel.count.toString()

        btn_change.setOnClickListener {
            userInfoViewModel.plus()
            userInfoViewModel.plusNew()
            tv_msg.text = userInfoViewModel.count.toString()
        }


        userInfoViewModel.countLiveData.observe(this, Observer {
            NLog.d(TAG, "countLiveData onChange: %s", it)
            tv_msg2.text = it.toString()
        })
    }
}