package com.apache.fastandroid.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.R
import com.apache.fastandroid.jetpack.InjectUtil
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.fragment_jetpack_viewmodel_restore_data.*

/**
 * Created by Jerry on 2020/11/1.
 */
class ViewModelRestoreDataFragment: BaseStatusFragmentNew() {

    //不能使用这种方式，这种方式生成的ViewModel 在横竖屏切换后数据不会保存
//    private val userInfoViewModel :UserInfoViewModel by lazy {
//        UserInfoViewModel(PostCardReporsity.get())
//    }

    private lateinit var userInfoViewModel:UserInfoViewModel

    override fun getLayoutId(): Int {
       return R.layout.fragment_jetpack_viewmodel_restore_data
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        userInfoViewModel = ViewModelProvider(this,InjectUtil.getUserModelFactory()).get(UserInfoViewModel::class.java)
        NLog.d(TAG, "userInfoViewModel: %s,savedInstanceSate:%s",userInfoViewModel,savedInstanceSate)
        btn_change.setOnClickListener {
            userInfoViewModel.plusNew()
        }


        userInfoViewModel.countLiveData.observe(this, Observer {
            NLog.d(TAG, "countLiveData onChange: %s", it)
            tv_msg2.text = it.toString()
        })
    }


}