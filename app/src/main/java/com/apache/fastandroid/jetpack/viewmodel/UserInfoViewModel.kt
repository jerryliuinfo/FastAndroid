package com.apache.fastandroid.jetpack.viewmodel

import android.widget.Toast
import androidx.lifecycle.*
import com.apache.fastandroid.demo.bean.UserBean
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.blankj.utilcode.util.Utils
import com.tesla.framework.common.util.log.NLog
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * Created by Jerry on 2020/11/1.
 */
class UserInfoViewModel(private val reporsity: UserReporsity):BaseStatusViewModel() {


    val countLiveData:MutableLiveData<Int> = MutableLiveData(0)

    val postCardLiveData = MutableLiveData<String>()

    fun plusNew(){
        countLiveData.value = (countLiveData.value?.plus(1))
    }

    private val addressInput = MutableLiveData<String>()



     fun loadPostCard(address: String){
        launch {
            NLog.d(NLog.TAG, "thread111: ${Thread.currentThread().name}")

            var result = reporsity.getPostCard(address)
            NLog.d(NLog.TAG, "thread333: ${Thread.currentThread().name}")

            postCardLiveData.value = result ?: ""
        }
    }



    private val _mUserInfo: MutableLiveData<UserBean> = MutableLiveData()
    val mUserInfo:LiveData<UserBean> = _mUserInfo

    val mMapLiveData:LiveData<String> = Transformations.map(_mUserInfo){ userBean ->
        userBean.age.toString()
    }

    val mSwitchMapSourceLiveData = MutableLiveData<Boolean>(false)


    val livedata1 = MutableLiveData<String>().apply {
        value = "LiveData1 数据"
    }
    val livedata2 = MutableLiveData<String>().apply {
        value = "LiveData2 数据"
    }
    val livedataSwitchMap:LiveData<String> = Transformations.switchMap(mSwitchMapSourceLiveData) {
        if (it){
            return@switchMap livedata1
        }
        return@switchMap livedata2
    }

    fun doSwitchMap(){
        mSwitchMapSourceLiveData.value =  ! mSwitchMapSourceLiveData.value!!
    }

    val mediatorLiveData = MediatorLiveData<String>()
    val mediatorLiveSource1 = MediatorLiveData<String>()
    val mediatorLiveSource2 = MediatorLiveData<String>()



    private fun loadUser(){
        NLog.d(TAG, "loadUser ---->")
        _mUserInfo.postValue(UserBean("Zhangsan",18))
    }


    fun changeAge(){
        var userInfo = _mUserInfo.value
        if (userInfo == null){
            userInfo = UserBean("jerry",18)
        }
        userInfo.apply {
            age = Random.nextInt(10)
        }
        _mUserInfo.value = userInfo
    }








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

