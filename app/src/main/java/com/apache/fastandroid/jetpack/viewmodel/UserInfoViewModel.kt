package com.apache.fastandroid.jetpack.viewmodel

import android.widget.Toast
import androidx.lifecycle.*
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.blankj.utilcode.util.Utils
import com.tesla.framework.component.logger.Logger
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Created by Jerry on 2020/11/1.
 */
class UserInfoViewModel(private val reporsity: UserReporsity):BaseStatusViewModel() {


    val countLiveData:MutableLiveData<Int> = MutableLiveData(0)

    val postCardLiveData: MutableLiveData<String> by lazy {
        //初始化调用
        loadPostCard("beijing")
        MutableLiveData<String>()
    }

    fun plusNew(){
        countLiveData.value = (countLiveData.value?.plus(1))
    }

    private val addressInput = MutableLiveData<String>()



     fun loadPostCard(address: String){
        launch {
            Logger.d( "thread111: ${Thread.currentThread().name}")

            var result = reporsity.getPostCard(address)
            Logger.d( "thread333: ${Thread.currentThread().name}")

            postCardLiveData.value = result ?: ""
        }
    }



    private val _mUserInfo: MutableLiveData<UserBean> = MutableLiveData()

    val mMapLiveData:LiveData<String> = Transformations.map(_mUserInfo){ userBean ->
        userBean.age.toString()
    }



    val livedata1 = MutableLiveData<String>().apply {
        value = "LiveData1 数据"
    }




    val mediatorLiveData = MediatorLiveData<String>()
    val mediatorLiveSource1 = MediatorLiveData<String>()
    val mediatorLiveSource2 = MediatorLiveData<String>()






    companion object{
         const val TAG = "UserInfoViewModel"
    }


    private fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            showLoading()
            block()
           dismissLoading()
        } catch (t: Throwable) {
            t.printStackTrace()
            Toast.makeText(Utils.getApp(), t.message, Toast.LENGTH_SHORT).show()
            dismissLoading()
        }
    }







}

