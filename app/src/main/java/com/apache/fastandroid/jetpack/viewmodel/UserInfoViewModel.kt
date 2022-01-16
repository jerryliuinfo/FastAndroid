package com.apache.fastandroid.jetpack.viewmodel

import android.widget.Toast
import androidx.lifecycle.*
import com.apache.fastandroid.demo.bean.user.UserBean
import com.apache.fastandroid.jetpack.BaseViewModel
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

    fun changeAddress(address:String){
        addressInput.value = address
    }



     fun loadPostCard(address: String){
        launch {
            NLog.d(NLog.TAG, "thread111: ${Thread.currentThread().name}")

            var result = reporsity.getPostCard(address)
            NLog.d(NLog.TAG, "thread333: ${Thread.currentThread().name}")

            postCardLiveData.value = result
        }
    }



    private val mUserInfo: MutableLiveData<UserBean> by lazy {
        MutableLiveData<UserBean>().also {
        }
    }

    val mUserName:LiveData<String> = Transformations.map(mUserInfo){ userBean ->
        userBean.name
    }



    private fun loadUser(){
        NLog.d(TAG, "loadUser ---->")
        mUserInfo.postValue(UserBean("Zhangsan",18))
    }


    fun changeValue(){
        val userBean = UserBean("Python:${Random.nextInt(20)}" ,Random.nextInt(10))
        mUserInfo.postValue(userBean)
    }



    val livedata1 = MutableLiveData<String>()
    val livedata2 = MutableLiveData<String>()

    val livedataSwitch = MutableLiveData<Boolean>()

    val livedataSwitchMap = Transformations.switchMap(livedataSwitch) {
        if (it){
            return@switchMap livedata1
        }
        return@switchMap livedata2
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

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }
}

