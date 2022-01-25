package com.apache.fastandroid.jetpack.livedata

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.apache.fastandroid.LogUtils
import com.apache.fastandroid.databinding.FragmentJetpackLivedataBinding
import com.apache.fastandroid.jetpack.reporsity.UserDao
import com.apache.fastandroid.jetpack.reporsity.UserNetwork
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.UserInfoViewModel
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.livedata.LiveDataBus
import com.tesla.framework.component.livedata.NetworkLiveData
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.networkStatus
import com.tesla.framework.ui.fragment.BaseVMFragment
import kotlin.random.Random

/**
 * Created by Jerry on 2020/11/5.
 */
class LiveDataBasicFragment : BaseVMFragment<FragmentJetpackLivedataBinding>(FragmentJetpackLivedataBinding::inflate){
    companion object{
        val TAG = "LiveDataBasicFragment"
    }

    val nameLiveData = MutableLiveData<String>()

    private val userViewModel by lazy {
        UserInfoViewModel(
            UserReporsity.getInstance(
            UserDao.getInstance(),
            UserNetwork().getInstance()))
    }
    private lateinit var priceLiveData: StockLiveData




    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        userViewModel.mediatorLiveData.addSource(userViewModel.mediatorLiveSource1){
            Logger.d("mediatorLiveData livedata1 onchange1: ${it}")
//            Logger.d("mediatorLiveData livedata1 onchange1: ${it}, livedata2: ${userViewModel.livedata2.value}")
//            val result = it + userViewModel.livedata2.value
//            val result = it
            userViewModel.mediatorLiveData.value = it + userViewModel.mediatorLiveSource2.value
        }

        userViewModel.mediatorLiveData.addSource(userViewModel.mediatorLiveSource2){
            Logger.d("mediatorLiveData livedata2 onchange1: ${it}")
//            viewBinding.textResult.text = it.toString() + userViewModel.mediatorLiveSource1.value
            userViewModel.mediatorLiveData.value = it + userViewModel.mediatorLiveSource1.value

        }



        priceLiveData = StockLiveData.get("")
        nameLiveData.observe(this, Observer<String> { t -> LogUtils.d("onChanged : $t")
            viewBinding.textResult.text = t
        })
        priceLiveData.observe(this){
            viewBinding.textResult.text = it.toString()

        }
       viewBinding.btnChange.setOnClickListener {
            nameLiveData.value = "jerry"
        }
        viewBinding.btnTransformMap.setOnClickListener {
            initTransformationsMap()
        }

        viewBinding.btnTransformSwitchMap.setOnClickListener {
            initTransformationsSwitchMap()
        }

        viewBinding.btnMediaLivedata.setOnClickListener {
            initMediaLiveData()
        }
        viewBinding.btnLivedataBus.setOnClickListener {
            initLiveDataBus();
        }

        viewBinding.btnLivedataListenNetwork.setOnClickListener {
            listenerNetworkByLivedata()
        }

    }

    private fun listenerNetworkByLivedata() {
        NetworkLiveData.getInstance().observe(this, Observer<Int> {
            viewBinding.textResult.text = it.networkStatus()
        })
    }

    private fun initLiveDataBus() {
        LiveDataBus.get().with("banner").observe(this,
            {
                viewBinding.textResult.text = it.toString()
            })
        LiveDataBus.get().with("banner").postValue("Hello : ${Random.nextInt(100)}" )
    }

    private fun initTransformationsMap(){
        userViewModel.mMapLiveData.observe(this){
            Logger.d("initTransformationsMap onchange: $it")
            viewBinding.textResult.text = it.toString()
        }
        userViewModel.changeAge()


    }

    private fun initTransformationsSwitchMap(){
        userViewModel.livedataSwitchMap.observe(this){
            viewBinding.textResult.text = it.toString()
        }
        userViewModel.doSwitchMap()
    }


    private var chagneLivedata1 = true
    private fun initMediaLiveData(){
        if (chagneLivedata1){
            userViewModel.mediatorLiveSource1.value = "Source1:${Random.nextInt(10)}"
        }else{
            userViewModel.mediatorLiveSource2.value = "Source2:${Random.nextInt(10) }"
        }
        chagneLivedata1 = !chagneLivedata1
        userViewModel.mediatorLiveData.observe (this, Observer {
            NLog.d("mediaLiveData onChange: %s", it)
            viewBinding.textResult.text = it

        })

    }

}