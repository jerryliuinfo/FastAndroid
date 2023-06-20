package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.apache.fastandroid.LogUtils
import com.apache.fastandroid.databinding.FragmentJetpackLivedataBinding
import com.apache.fastandroid.jetpack.reporsity.UserDao
import com.apache.fastandroid.jetpack.reporsity.UserNetwork
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import com.tesla.framework.component.livedata.NetworkLiveData
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.networkStatus
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlin.random.Random

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataBasicFragment : BaseBindingFragment<FragmentJetpackLivedataBinding>(FragmentJetpackLivedataBinding::inflate){
    companion object{
        val TAG = "LiveDataBasicFragment"
    }


    private val userViewModel by lazy {
        UserInfoViewModel(
            UserReporsity.getInstance(
            UserDao.getInstance(),
            UserNetwork().getInstance()))
    }

    private val mLiveDataViewModel by viewModels<LiveDataViewModel>()

    private lateinit var priceLiveData: StockLiveData




    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        priceLiveData = StockLiveData.get("")


        priceLiveData.observe(this){
            mBinding.textResult.text = it.toString()
        }


        mLiveDataViewModel.mUserInfo.observe(this){
            mBinding.textResult.text = "${ it.name }:${it.age}"
        }


       mBinding.btnChange.setOnClickListener {
           mLiveDataViewModel.changeData()
        }
        mBinding.btnTransformMap.setOnClickListener {
            initTransformationsMap()
        }

        mBinding.btnTransformSwitchMap.setOnClickListener {
            initTransformationsSwitchMap()
        }

        mBinding.btnMediaLivedata.setOnClickListener {
            initMediaLiveData()
        }
        mBinding.btnLivedataBus.setOnClickListener {
            initLiveDataBus();
        }

        mBinding.btnLivedataListenNetwork.setOnClickListener {
            listenerNetworkByLivedata()
        }

        mBinding.btnLivedataSetnull.setOnClickListener {
            userViewModel.livedata1.value= null
        }

        userViewModel.livedata1.observe(this){
            Logger.d("livedata1 value:${it}")
        }

    }

    private fun listenerNetworkByLivedata() {
        NetworkLiveData.getInstance().observe(this, Observer<Int> {
            mBinding.textResult.text = it.networkStatus()
        })
    }

    private fun initLiveDataBus() {

    }

    private fun initTransformationsMap(){
        mLiveDataViewModel.mAgeLiveData.observe(this){
            Logger.d("initTransformationsMap onchange: $it")
            mBinding.textResult.text = it.toString()
        }
        mLiveDataViewModel.changeData()
    }

    private fun initTransformationsSwitchMap(){
        mLiveDataViewModel.switchMapLivedata.observe(this){
            mBinding.textResult.text = it.toString()
        }
        mLiveDataViewModel.doSwitchMap()
    }


    private fun initMediaLiveData(){
        mLiveDataViewModel.changeMediaLiveData()
        mLiveDataViewModel.mediatorLiveData.observe (this, Observer {
            Logger.d("mediaLiveData onChange: %s", it)
            mBinding.textResult.text = it
        })

    }

}